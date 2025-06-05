package org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.toJavaLocalDateTime
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.presentation.general.composables.AddRow
import org.homesharing.lockbox.presentation.general.composables.LockBoxCircularProgress
import org.homesharing.lockbox.presentation.general.composables.RoundText
import org.homesharing.lockbox.presentation.general.composables.TitledTopWithCloseAndBackButtons
import org.homesharing.lockbox.presentation.general.composables.basic.BasicButton
import org.homesharing.lockbox.presentation.general.composables.basic.BasicButtonStyle
import org.homesharing.lockbox.presentation.general.composables.basic.BasicHorizontalDivider
import org.homesharing.lockbox.presentation.general.composables.basic.BasicModal
import org.homesharing.lockbox.presentation.general.composables.inputs.DateInput
import org.homesharing.lockbox.presentation.general.composables.inputs.LockBoxPropertyInput
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@Composable
internal fun AccessModalSheet(
    onDismiss: () -> Unit,
    accessList: List<Access>,
    step: AccessModalSheetStep,
    changeStep: (AccessModalSheetStep) -> Unit,
    toAddGuest: () -> Unit,
    isLoading: Boolean,
    guestName: String,
    onChangeStartDate: (Long) -> Unit,
    onChangeEndDate: (Long) -> Unit,
    onChangeGuestName: (String) -> Unit,
    addGuest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val formatter = remember { DateTimeFormatter.ofPattern("d MMM yyyy") }
    BasicModal(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        TitledTopWithCloseAndBackButtons(
            title = stringResource(R.string.access_device),
            subtitle = stringResource(R.string.add_user),
            onDismiss = {
                if (step is AccessModalSheetStep.GuestList) onDismiss()
                else changeStep(AccessModalSheetStep.GuestList)
            },
            modifier = Modifier.fillMaxWidth()
        )

        BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

        AnimatedContent(
            targetState = step
        ) { step ->
            when (step) {
                is AccessModalSheetStep.AddGuest -> {
                    AddGuestContent(
                        lockboxName = step.lockboxName,
                        lockboxAddress = step.lockboxAddress,
                        guestName = guestName,
                        onChangeGuestName = onChangeGuestName,
                        onChangeStartDate = onChangeStartDate,
                        onChangeEndDate = onChangeEndDate,
                        addGuest = addGuest,
                        onNavBack = { changeStep(AccessModalSheetStep.GuestList) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                AccessModalSheetStep.GuestList -> {
                    GuestListContent(
                        isLoading = isLoading,
                        accessList = accessList,
                        formatter = formatter,
                        toAddGuest = toAddGuest,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddGuestContent(
    lockboxName: String,
    lockboxAddress: String,
    guestName: String,
    onChangeGuestName: (String) -> Unit,
    onChangeStartDate: (Long) -> Unit,
    onChangeEndDate: (Long) -> Unit,
    addGuest: () -> Unit,
    onNavBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 48.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = lockboxName,
                textAlign = TextAlign.Center,
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.grey100
            )

            Text(
                text = lockboxAddress,
                textAlign = TextAlign.Center,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.grey500
            )
        }

        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTime
        }
        val currentYear = calendar.get(Calendar.YEAR)
        val locale = LocalConfiguration.current.locales[0]!!
        val formatter = remember { SimpleDateFormat("dd.MM.yyyy", locale) }

        val startAvailableDates = remember {
            object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val day = Instant.ofEpochMilli(utcTimeMillis)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate()
                        .dayOfYear

                    return day >= calendar.get(Calendar.DAY_OF_YEAR)
                }

                override fun isSelectableYear(year: Int): Boolean = year >= currentYear
            }
        }

        val startDatePickerState = rememberDatePickerState(
            yearRange = currentYear..currentYear + 100,
            initialDisplayMode = DisplayMode.Picker,
            selectableDates = startAvailableDates
        )
        val selectedStartDate: String? = startDatePickerState.selectedDateMillis?.let { time ->
            onChangeStartDate(time)
            formatter.format(Date(time))
        }

        var showStartDatePicker by remember { mutableStateOf(false) }
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(R.string.date_and_time_of_access_start),
                color = LockBoxTheme.colors.grey100,
                style = LockBoxTheme.typography.subHeadlineLarge,
            )

            DateInput(
                date = selectedStartDate.orEmpty(),
                onFieldClick = { showStartDatePicker = !showStartDatePicker },
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(showStartDatePicker) {
                DockedDatePicker(
                    datePickerState = startDatePickerState
                )
            }
        }

        val endAvailableDates = remember {
            object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val dayLocalDate = Instant.ofEpochMilli(utcTimeMillis)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate()
                    val day = dayLocalDate.dayOfYear

                    val startDay = startDatePickerState.selectedDateMillis?.let { selected ->
                        Instant.ofEpochMilli(selected)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                    }

                    return if (startDay != null) dayLocalDate >= startDay
                    else day >= calendar.get(Calendar.DAY_OF_YEAR)
                }

                override fun isSelectableYear(year: Int): Boolean {
                    val selectedStartYear =
                        startDatePickerState.selectedDateMillis?.let { selected ->
                            Instant.ofEpochMilli(selected)
                                .atZone(ZoneId.of("UTC"))
                                .toLocalDate()
                                .year
                        }

                    return if (selectedStartYear != null) year >= currentYear && year >= selectedStartYear
                    else year >= currentYear
                }
            }
        }

        val endDatePickerState = rememberDatePickerState(
            yearRange = currentYear..currentYear + 100,
            initialDisplayMode = DisplayMode.Picker,
            selectableDates = endAvailableDates
        )
        val selectedEndDate: String? = endDatePickerState.selectedDateMillis?.let { time ->
            onChangeEndDate(time)
            formatter.format(Date(time))
        }

        var showEndDatePicker by remember { mutableStateOf(false) }
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(R.string.date_and_time_of_access_end),
                color = LockBoxTheme.colors.grey100,
                style = LockBoxTheme.typography.subHeadlineLarge
            )

            DateInput(
                date = selectedEndDate.orEmpty(),
                onFieldClick = { showEndDatePicker = !showEndDatePicker },
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(showEndDatePicker) {
                DockedDatePicker(
                    datePickerState = endDatePickerState
                )
            }
        }

        val keyboardManager = LocalSoftwareKeyboardController.current

        Column(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(R.string.guest_name),
                color = LockBoxTheme.colors.grey100,
                style = LockBoxTheme.typography.subHeadlineLarge
            )

            LockBoxPropertyInput(
                value = guestName,
                onValueChange = onChangeGuestName,
                placeholderText = stringResource(R.string.guest_name_placeholder),
                onDone = { keyboardManager?.hide() },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CopyButton(onClick = addGuest, modifier = Modifier.fillMaxWidth())

            BasicButton(
                onClick = onNavBack,
                text = stringResource(R.string.cancel),
                style = BasicButtonStyle.grey(),
                paddingValues = PaddingValues(14.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CopyButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .clip(LockBoxTheme.shape.roundedCornerShape8)
            .background(LockBoxTheme.colors.green500)
            .clickable { onClick() }
            .padding(12.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Icon(
            painter = painterResource(R.drawable.copy),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey900,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = stringResource(R.string.copy_link),
            color = LockBoxTheme.colors.grey900,
            style = LockBoxTheme.typography.headlineLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DockedDatePicker(
    datePickerState: DatePickerState,
) {
    DatePicker(
        modifier = Modifier.clip(LockBoxTheme.shape.roundedCornerShape8),
        state = datePickerState,
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            containerColor = LockBoxTheme.colors.grey800,
            yearContentColor = LockBoxTheme.colors.grey100,
            subheadContentColor = LockBoxTheme.colors.grey100,
            dayContentColor = LockBoxTheme.colors.grey100,
            weekdayContentColor = LockBoxTheme.colors.grey100,
            titleContentColor = LockBoxTheme.colors.grey100,
            todayContentColor = LockBoxTheme.colors.grey100,
            navigationContentColor = LockBoxTheme.colors.grey100,
            headlineContentColor = LockBoxTheme.colors.grey100,
            selectedDayContentColor = LockBoxTheme.colors.grey100,
            selectedYearContentColor = LockBoxTheme.colors.grey100,
            selectedDayContainerColor = LockBoxTheme.colors.grey700,
            selectedYearContainerColor = LockBoxTheme.colors.grey700,
            currentYearContentColor = LockBoxTheme.colors.grey50,
            todayDateBorderColor = LockBoxTheme.colors.grey50,
            dateTextFieldColors = TextFieldDefaults.colors(
                focusedTextColor = LockBoxTheme.colors.grey100,
                unfocusedTextColor = LockBoxTheme.colors.grey100,
                errorTextColor = LockBoxTheme.colors.red600,
                cursorColor = LockBoxTheme.colors.white,
                errorCursorColor = LockBoxTheme.colors.red600,
                focusedContainerColor = LockBoxTheme.colors.grey800,
                unfocusedContainerColor = LockBoxTheme.colors.grey800,
                errorContainerColor = LockBoxTheme.colors.grey800,
                focusedIndicatorColor = LockBoxTheme.colors.grey100,
                unfocusedIndicatorColor = LockBoxTheme.colors.grey500,
                errorIndicatorColor = LockBoxTheme.colors.red600,
                focusedLabelColor = LockBoxTheme.colors.grey100,
                unfocusedLabelColor = LockBoxTheme.colors.grey500,
            ),
        ),
    )
}

@Composable
private fun GuestListContent(
    isLoading: Boolean,
    accessList: List<Access>,
    formatter: DateTimeFormatter,
    toAddGuest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = isLoading,
        modifier = modifier
    ) { loading ->
        when (loading) {
            true -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LockBoxCircularProgress()
                }
            }

            false -> {
                LazyColumn(
                    contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        count = accessList.size,
                        key = { index -> accessList[index].macAddress }
                    ) { index ->
                        Access(
                            access = accessList[index],
                            formatter = formatter,
                            modifier = Modifier.fillMaxWidth()
                        )

                        BasicHorizontalDivider()
                    }

                    item {
                        AddRow(
                            onAdd = toAddGuest,
                            text = stringResource(R.string.add_user),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Access(
    access: Access,
    formatter: DateTimeFormatter,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundText(
            text = access.name ?: stringResource(R.string.guest),
            modifier = Modifier.size(40.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = access.name ?: stringResource(R.string.guest),
                color = LockBoxTheme.colors.white,
                style = LockBoxTheme.typography.headlineLarge
            )

            val endDateText =
                formatter.format(access.endTime.toJavaLocalDateTime().toLocalDate())
            Text(
                text = endDateText,
                color = LockBoxTheme.colors.grey500,
                style = LockBoxTheme.typography.footnote
            )

        }

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            tint = LockBoxTheme.colors.grey500,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
