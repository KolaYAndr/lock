package org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet

import android.content.ClipData
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.presentation.uikit.ActionOnSharedFlow
import org.homesharing.lockbox.presentation.uikit.composables.AddRow
import org.homesharing.lockbox.presentation.uikit.composables.RoundText
import org.homesharing.lockbox.presentation.uikit.composables.TitledTopWithCloseAndBackButtons
import org.homesharing.lockbox.presentation.uikit.composables.core.LockBoxCircularProgress
import org.homesharing.lockbox.presentation.uikit.composables.core.LockboxHorizontalDivider
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButtonStyle
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxModal
import org.homesharing.lockbox.presentation.uikit.composables.core.inputs.LockBoxPropertyInput
import org.homesharing.lockbox.presentation.uikit.composables.core.inputs.LockboxDateInput
import org.homesharing.lockbox.presentation.uikit.composables.core.inputs.LockboxTimeInput
import org.homesharing.lockbox.presentation.uikit.composables.core.pickers.LockboxDatePicker
import org.homesharing.lockbox.presentation.uikit.composables.core.pickers.LockboxTimePicker
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

private const val URL_PREFIX = "https://lockboxes.ru/open"

@Composable
internal fun AccessModalSheet(
    macAddress: String,
    boxName: String,
    address: String,
    onDismiss: () -> Unit,
    accessModalSheetViewModel: AccessModalSheetViewModel = koinViewModel { parametersOf(macAddress) },
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val clipboard = LocalClipboard.current
    val label = stringResource(R.string.link_copied)
    ActionOnSharedFlow(
        lifecycleOwner = lifecycleOwner,
        flow = accessModalSheetViewModel.tokenData
    ) { token ->
        clipboard.setClipEntry(ClipEntry(ClipData.newPlainText(label, "$URL_PREFIX/$token")))
    }

    val accessModalSheetState by accessModalSheetViewModel.uiState.collectAsStateWithLifecycle()
    AccessModalSheet(
        onDismiss = onDismiss,
        lockboxName = boxName,
        lockboxAddress = address,
        accessList = accessModalSheetState.accesses,
        step = accessModalSheetState.step,
        changeStep = accessModalSheetViewModel::toStep,
        isLoading = accessModalSheetState.loading,
        guestName = accessModalSheetState.guestName,
        onChangeGuestName = accessModalSheetViewModel::onChangeGuestName,
        onChangeStartDate = accessModalSheetViewModel::onChangeStartDate,
        onChangeStartTime = accessModalSheetViewModel::onChangeStartTime,
        onChangeEndDate = accessModalSheetViewModel::onChangeEndDate,
        onChangeEndTime = accessModalSheetViewModel::onChangeEndTime,
        addGuest = accessModalSheetViewModel::addGuest,
        onCopyLink = accessModalSheetViewModel::emitLink,
        onRevokeToken = accessModalSheetViewModel::revokeToken,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun AccessModalSheet(
    onDismiss: () -> Unit,
    lockboxName: String,
    lockboxAddress: String,
    accessList: List<Access>,
    step: AccessModalSheetStep,
    changeStep: (AccessModalSheetStep) -> Unit,
    isLoading: Boolean,
    guestName: String,
    onChangeStartDate: (Long) -> Unit,
    onChangeStartTime: (Long) -> Unit,
    onChangeEndDate: (Long) -> Unit,
    onChangeEndTime: (Long) -> Unit,
    onChangeGuestName: (String) -> Unit,
    addGuest: () -> Unit,
    onCopyLink: (String) -> Unit,
    onRevokeToken: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val formatter = remember { DateTimeFormatter.ofPattern("d MMM yyyy") }
    LockboxModal(
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

        LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())

        AnimatedContent(
            targetState = step
        ) { step ->
            when (step) {
                is AccessModalSheetStep.AddGuest -> {
                    AddGuestContent(
                        guestName = guestName,
                        onChangeGuestName = onChangeGuestName,
                        onChangeStartDate = onChangeStartDate,
                        onChangeStartTime = onChangeStartTime,
                        onChangeEndDate = onChangeEndDate,
                        onChangeEndTime = onChangeEndTime,
                        addGuest = addGuest,
                        onNavBack = { changeStep(AccessModalSheetStep.GuestList) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is AccessModalSheetStep.GuestInfo -> {
                    GuestInfoContent(
                        lockboxName = lockboxName,
                        lockboxAddress = lockboxAddress,
                        access = step.access,
                        onCopyLink = onCopyLink,
                        onUnbindUser = onRevokeToken,
                        onNavBack = { changeStep(AccessModalSheetStep.GuestList) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                AccessModalSheetStep.GuestList -> {
                    GuestListContent(
                        isLoading = isLoading,
                        accessList = accessList,
                        formatter = formatter,
                        toAddGuest = { changeStep(AccessModalSheetStep.AddGuest) },
                        toAccessInfo = { access ->
                            changeStep(AccessModalSheetStep.GuestInfo(access))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun GuestInfoContent(
    lockboxName: String,
    lockboxAddress: String,
    access: Access,
    onCopyLink: (String) -> Unit,
    onUnbindUser: (String) -> Unit,
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

        val locale = LocalConfiguration.current.locales[0]!!
        val formatter = remember { DateTimeFormatter.ofPattern("dd.MM.yyyy", locale) }

        val selectedStartDate = access.startTime.toJavaLocalDateTime().format(formatter)
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

            LockboxDateInput(
                date = selectedStartDate,
                onFieldClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }

        val selectedEndDate = access.endTime.toJavaLocalDateTime().format(formatter)
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
                style = LockBoxTheme.typography.subHeadlineLarge,
            )

            LockboxDateInput(
                date = selectedEndDate,
                onFieldClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }

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
                value = access.name,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CopyButton(
                onClick = { onCopyLink(access.id) },
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            )

            LockboxButton(
                onClick = {
                    onUnbindUser(access.id)
                    onNavBack()
                },
                text = stringResource(R.string.unbind_user),
                style = LockboxButtonStyle.red(),
                paddingValues = PaddingValues(14.dp),
                modifier = Modifier.fillMaxWidth()
            )

            LockboxButton(
                onClick = onNavBack,
                text = stringResource(R.string.back),
                style = LockboxButtonStyle.grey(),
                paddingValues = PaddingValues(14.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddGuestContent(
    guestName: String,
    onChangeGuestName: (String) -> Unit,
    onChangeStartDate: (Long) -> Unit,
    onChangeStartTime: (Long) -> Unit,
    onChangeEndDate: (Long) -> Unit,
    onChangeEndTime: (Long) -> Unit,
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
            Icon(
                painter = painterResource(R.drawable.user_plus),
                contentDescription = null,
                tint = LockBoxTheme.colors.grey500,
                modifier = Modifier
                    .padding(4.dp)
                    .size(48.dp)
            )

            Text(
                text = stringResource(R.string.add_user),
                textAlign = TextAlign.Center,
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.grey100
            )

            Text(
                text = stringResource(R.string.set_parameters_and_send_link),
                textAlign = TextAlign.Center,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.grey500
            )
        }

        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply { timeInMillis = currentTime }
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

        val startTimePickerState = rememberTimePickerState(
            initialHour = calendar.get(Calendar.HOUR_OF_DAY),
            initialMinute = calendar.get(Calendar.MINUTE),
            is24Hour = true,
        )
        val selectedStartTime = stringResource(
            R.string.selected_time,
            startTimePickerState.hour,
            startTimePickerState.minute
        )
        LaunchedEffect(startTimePickerState.hour, startTimePickerState.minute) {
            onChangeStartTime(startTimePickerState.toMillis())
        }

        var showStartDatePicker by remember { mutableStateOf(false) }
        var showStartTimePicker by remember { mutableStateOf(false) }
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
            ) {
                LockboxDateInput(
                    date = selectedStartDate.orEmpty(),
                    onFieldClick = {
                        showStartDatePicker = !showStartDatePicker
                        showStartTimePicker = false
                    }
                )

                LockboxTimeInput(
                    time = selectedStartTime,
                    onFieldClick = {
                        showStartTimePicker = !showStartTimePicker
                        showStartDatePicker = false
                    }
                )
            }

            AnimatedVisibility(showStartDatePicker) {
                LockboxDatePicker(datePickerState = startDatePickerState)
            }

            AnimatedVisibility(showStartTimePicker) {
                LockboxTimePicker(
                    timePickerState = startTimePickerState,
                    modifier = Modifier.fillMaxWidth()
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

        val endTimePickerState = rememberTimePickerState(is24Hour = true)
        val selectedEndTime = stringResource(
            R.string.selected_time,
            endTimePickerState.hour,
            endTimePickerState.minute
        )

        LaunchedEffect(endTimePickerState.hour, endTimePickerState.minute) {
            onChangeEndTime(endTimePickerState.toMillis())
        }

        var showEndDatePicker by remember { mutableStateOf(false) }
        var showEndTimePicker by remember { mutableStateOf(false) }
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
            ) {
                LockboxDateInput(
                    date = selectedEndDate.orEmpty(),
                    onFieldClick = {
                        showEndDatePicker = !showEndDatePicker
                        showEndTimePicker = false
                    }
                )

                LockboxTimeInput(
                    time = selectedEndTime,
                    onFieldClick = {
                        showEndTimePicker = !showEndTimePicker
                        showEndDatePicker = false
                    }
                )
            }

            AnimatedVisibility(showEndDatePicker) {
                LockboxDatePicker(datePickerState = endDatePickerState)
            }

            AnimatedVisibility(showEndTimePicker) {
                LockboxTimePicker(
                    timePickerState = endTimePickerState,
                    modifier = Modifier.fillMaxWidth()
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
                keyboardActions = KeyboardActions(
                    onAny = { keyboardManager?.hide() }
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val start = startDatePickerState.selectedDateMillis
            val end = endDatePickerState.selectedDateMillis
            CopyButton(
                onClick = addGuest,
                enabled = start != null && end != null && start <= end,
                modifier = Modifier.fillMaxWidth()
            )

            LockboxButton(
                onClick = onNavBack,
                text = stringResource(R.string.cancel),
                style = LockboxButtonStyle.grey(),
                paddingValues = PaddingValues(14.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CopyButton(onClick: () -> Unit, enabled: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .clip(LockBoxTheme.shape.roundedCornerShape8)
            .background(if (enabled) LockBoxTheme.colors.green500 else LockBoxTheme.colors.grey800)
            .clickable { if (enabled) onClick() }
            .padding(12.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Icon(
            painter = painterResource(R.drawable.copy),
            contentDescription = null,
            tint = if (enabled) LockBoxTheme.colors.grey900 else LockBoxTheme.colors.grey700,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = stringResource(R.string.copy_link),
            color = if (enabled) LockBoxTheme.colors.grey900 else LockBoxTheme.colors.grey700,
            style = LockBoxTheme.typography.headlineLarge
        )
    }
}

@Composable
private fun GuestListContent(
    isLoading: Boolean,
    accessList: List<Access>,
    formatter: DateTimeFormatter,
    toAddGuest: () -> Unit,
    toAccessInfo: (Access) -> Unit,
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
                        key = { index -> accessList[index].id }
                    ) { index ->
                        Access(
                            access = accessList[index],
                            formatter = formatter,
                            onAccessClick = { toAccessInfo(accessList[index]) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        LockboxHorizontalDivider()
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
    onAccessClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .clip(LockBoxTheme.shape.roundedCornerShape8)
            .clickable { onAccessClick() }
            .padding(vertical = 10.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundText(
            text = access.name,
            modifier = Modifier.size(40.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = access.name,
                color = LockBoxTheme.colors.constantWhite,
                style = LockBoxTheme.typography.headlineLarge
            )

            val endDateText =
                formatter.format(access.endTime.toJavaLocalDateTime().toLocalDate())
            Text(
                text = stringResource(R.string.until_date, endDateText),
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

@OptIn(ExperimentalMaterial3Api::class)
private fun TimePickerState.toMillis() = (this.hour * 3_600_000 + this.minute * 6_000).toLong()

@Preview
@Composable
private fun PreviewAccess() {
    LockBoxTheme {
        val formatter = remember { DateTimeFormatter.ofPattern("d MMM yyyy") }
        Access(
            access = Access(
                name = "",
                startTime = LocalDateTime.parse("2004-09-16T23:59:58.75"),
                endTime = LocalDateTime.parse("2004-09-16T23:59:58.75"),
                macAddress = "1A:2B",
                id = "123"
            ),
            formatter = formatter,
            onAccessClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewCopyButton() {
    LockBoxTheme {
        CopyButton(
            onClick = {},
            enabled = true,
        )
    }
}

@Preview
@Composable
private fun PreviewCopyButtonDisabled() {
    LockBoxTheme {
        CopyButton(
            onClick = {},
            enabled = false,
        )
    }
}

@Preview
@Composable
private fun PreviewGuestListContent() {
    LockBoxTheme {
        val formatter = remember { DateTimeFormatter.ofPattern("d MMM yyyy") }
        val list = listOf(
            Access(
                name = "",
                startTime = LocalDateTime.parse("2004-09-16T23:59:58.75"),
                endTime = LocalDateTime.parse("2004-09-16T23:59:58.75"),
                macAddress = "1A:2B",
                id = "123"
            ),
        )

        GuestListContent(
            isLoading = false,
            accessList = list,
            formatter = formatter,
            toAddGuest = {},
            toAccessInfo = {},
        )
    }
}

@Preview
@Composable
private fun PreviewGuestListContentLoading() {
    LockBoxTheme {
        val formatter = remember { DateTimeFormatter.ofPattern("d MMM yyyy") }
        val list = listOf(
            Access(
                name = "",
                startTime = LocalDateTime.parse("2004-09-16T23:59:58.75"),
                endTime = LocalDateTime.parse("2004-09-16T23:59:58.75"),
                macAddress = "1A:2B",
                id = "123"
            ),
        )

        GuestListContent(
            isLoading = true,
            accessList = list,
            formatter = formatter,
            toAddGuest = {},
            toAccessInfo = {},
        )
    }
}

@Preview
@Composable
private fun PreviewAddGuestListContent() {
    LockBoxTheme {
        AddGuestContent(
            guestName = "Гость",
            onChangeGuestName = {},
            onChangeStartDate = {},
            onChangeStartTime = {},
            onChangeEndDate = {},
            onChangeEndTime = {},
            addGuest = {},
            onNavBack = {},
        )
    }
}

@Preview
@Composable
private fun PreviewGuestInfoContent() {
    LockBoxTheme {
        GuestInfoContent(
            lockboxName = "Dom",
            lockboxAddress = "ul. Doma",
            access = Access(
                name = "",
                startTime = LocalDateTime.parse("2004-09-16T23:59:58.75"),
                endTime = LocalDateTime.parse("2004-09-16T23:59:58.75"),
                macAddress = "1A:2B",
                id = "123"
            ),
            onCopyLink = {},
            onUnbindUser = {},
            onNavBack = {},
        )
    }
}
