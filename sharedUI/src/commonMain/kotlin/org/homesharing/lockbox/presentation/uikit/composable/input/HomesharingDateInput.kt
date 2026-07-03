package org.homesharing.lockbox.presentation.uikit.composable.input

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.check_in_dates
import lockbox.sharedui.generated.resources.check_in_dates_separated
import lockbox.sharedui.generated.resources.ic_calendar_days
import lockbox.sharedui.generated.resources.ic_close
import org.homesharing.lockbox.presentation.uikit.composable.date_picker.HomesharingDatePicker
import org.homesharing.lockbox.presentation.uikit.modifier.paddedBorder
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val checkInDateFormat = LocalDate.Format {
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    day(Padding.NONE)
    chars(", ")
    year()
}

private fun formatCheckInDate(dateMillis: Long?): String {
    dateMillis ?: return ""
    val date = Instant.fromEpochMilliseconds(dateMillis)
        .toLocalDateTime(TimeZone.UTC)
        .date
    return checkInDateFormat.format(date)
}

@Composable
internal fun HomesharingDateInput(
    startDateMillis: Long?,
    endDateMillis: Long?,
    onDateRangeChanged: (startDateMillis: Long?, endDateMillis: Long?) -> Unit,
    onClearDates: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val borderColor by animateColorAsState(
            targetValue = if (showDatePicker) {
                HomesharingTheme.colors.black.copy(alpha = .5f)
            } else {
                Color.Transparent
            }
        )

        val currentLocalDate = remember {
            Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
        }
        val currentYear = remember(currentLocalDate) { currentLocalDate.year }

        val availableDates = remember {
            object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val day = Instant.fromEpochMilliseconds(utcTimeMillis)
                        .toLocalDateTime(TimeZone.UTC)
                        .date

                    return day >= currentLocalDate
                }

                override fun isSelectableYear(year: Int): Boolean = year >= currentYear
            }
        }

        val state = rememberDateRangePickerState(
            yearRange = currentYear..currentYear + 30,
            initialDisplayMode = DisplayMode.Picker,
            selectableDates = availableDates,
        )

        LaunchedEffect(state) {
            snapshotFlow {
                state.selectedStartDateMillis to state.selectedEndDateMillis
            }.collect { (selectedStartDateMillis, selectedEndDateMillis) ->
                onDateRangeChanged(
                    selectedStartDateMillis,
                    selectedEndDateMillis,
                )
            }
        }

        LaunchedEffect(startDateMillis, endDateMillis) {
            val pickerStartDateMillis = state.selectedStartDateMillis
            val pickerEndDateMillis = state.selectedEndDateMillis

            if (
                pickerStartDateMillis != startDateMillis ||
                pickerEndDateMillis != endDateMillis
            ) {
                state.setSelection(
                    startDateMillis = startDateMillis,
                    endDateMillis = endDateMillis,
                )
            }
        }

        val startDate = remember(state.selectedStartDateMillis) {
            formatCheckInDate(state.selectedStartDateMillis)
        }

        val endDate = remember(state.selectedEndDateMillis) {
            formatCheckInDate(state.selectedEndDateMillis)
        }
        val value = stringResource(
            Res.string.check_in_dates_separated,
            startDate,
            endDate,
        ).takeIf { startDate.isNotEmpty() || endDate.isNotEmpty() }.orEmpty()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    role = Role.Button,
                    indication = null,
                    interactionSource = null,
                ) {
                    showDatePicker = !showDatePicker
                }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                isError = false,
                enabled = false,
                readOnly = true,
                shape = HomesharingTheme.shape.roundedCornerShape16,
                placeholder = {
                    Text(
                        text = stringResource(Res.string.check_in_dates),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_calendar_days),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                },
                trailingIcon = if (showDatePicker) {
                    {
                        IconButton(
                            onClick = onClearDates,
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = HomesharingTheme.colors.black.copy(alpha = .5f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_close),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                } else {
                    null
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = HomesharingTheme.colors.white,
                    focusedBorderColor = HomesharingTheme.colors.black.copy(alpha = .2f),
                    focusedPlaceholderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
                    focusedTextColor = HomesharingTheme.colors.black,
                    focusedLabelColor = HomesharingTheme.colors.black.copy(alpha = .5f),
                    focusedLeadingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
                    focusedTrailingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
                    cursorColor = HomesharingTheme.colors.black,
                    disabledTextColor = HomesharingTheme.colors.black,
                    disabledLabelColor = HomesharingTheme.colors.black.copy(alpha = .5f),
                    disabledPlaceholderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
                    disabledBorderColor = HomesharingTheme.colors.black.copy(alpha = .2f),
                    disabledLeadingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
                    disabledTrailingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
                    disabledContainerColor = HomesharingTheme.colors.white,
                    unfocusedTextColor = HomesharingTheme.colors.black,
                    unfocusedLabelColor = HomesharingTheme.colors.black.copy(alpha = .5f),
                    unfocusedPlaceholderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
                    unfocusedBorderColor = HomesharingTheme.colors.black.copy(alpha = .1f),
                    unfocusedContainerColor = HomesharingTheme.colors.white,
                    unfocusedLeadingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
                    unfocusedTrailingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
                    errorTextColor = HomesharingTheme.colors.black,
                    errorLabelColor = HomesharingTheme.colors.black.copy(alpha = .5f),
                    errorCursorColor = HomesharingTheme.colors.black,
                    errorBorderColor = HomesharingTheme.colors.red,
                    errorContainerColor = HomesharingTheme.colors.white,
                    errorPlaceholderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
                    errorLeadingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
                    errorTrailingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .paddedBorder(borderColor),
            )
        }

        AnimatedVisibility(
            visible = showDatePicker,
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            HomesharingDatePicker(
                datePickerState = state,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomesharingDateInput() {
    HomesharingTheme {
        var startDateMillis by remember { mutableStateOf<Long?>(null) }
        var endDateMillis by remember { mutableStateOf<Long?>(null) }

        HomesharingDateInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp),
            startDateMillis = startDateMillis,
            endDateMillis = endDateMillis,
            onDateRangeChanged = { start, end ->
                startDateMillis = start
                endDateMillis = end
            },
            onClearDates = {
                startDateMillis = null
                endDateMillis = null
            },
        )
    }
}