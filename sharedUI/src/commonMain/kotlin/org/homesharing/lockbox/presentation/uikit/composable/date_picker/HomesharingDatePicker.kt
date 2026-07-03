package org.homesharing.lockbox.presentation.uikit.composable.date_picker

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.check_in_dates
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomesharingDatePicker(
    datePickerState: DateRangePickerState,
    modifier: Modifier = Modifier,
) {
    DateRangePicker(
        modifier = modifier
            .heightIn(max = 360.dp)
            .clip(HomesharingTheme.shape.roundedCornerShape24)
            .border(
                width = 0.5.dp,
                color = HomesharingTheme.colors.black,
                shape = HomesharingTheme.shape.roundedCornerShape24
            ),
        state = datePickerState,
        showModeToggle = false,
        title = null,
        headline = {
            Text(
                text = stringResource(Res.string.check_in_dates),
                style = HomesharingTheme.typography.titleSmall,
                color = HomesharingTheme.colors.black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        },
        colors = DatePickerDefaults.colors(
            containerColor = HomesharingTheme.colors.white,
            yearContentColor = HomesharingTheme.colors.black,
            subheadContentColor = HomesharingTheme.colors.black,
            dayContentColor = HomesharingTheme.colors.black,
            weekdayContentColor = HomesharingTheme.colors.black,
            titleContentColor = HomesharingTheme.colors.black,
            todayContentColor = HomesharingTheme.colors.black,
            navigationContentColor = HomesharingTheme.colors.black,
            headlineContentColor = HomesharingTheme.colors.black,
            selectedDayContentColor = HomesharingTheme.colors.white,
            selectedYearContentColor = HomesharingTheme.colors.white,
            selectedDayContainerColor = HomesharingTheme.colors.black,
            selectedYearContainerColor = HomesharingTheme.colors.black,
            currentYearContentColor = HomesharingTheme.colors.black,
            todayDateBorderColor = HomesharingTheme.colors.black,
            dayInSelectionRangeContainerColor = HomesharingTheme.colors.black.copy(alpha = .1f),
            dayInSelectionRangeContentColor = HomesharingTheme.colors.black,
            dateTextFieldColors = TextFieldDefaults.colors(
                focusedTextColor = HomesharingTheme.colors.black,
                unfocusedTextColor = HomesharingTheme.colors.black,
                errorTextColor = HomesharingTheme.colors.black,
                cursorColor = HomesharingTheme.colors.black,
                errorCursorColor = HomesharingTheme.colors.black,
                focusedContainerColor = HomesharingTheme.colors.white,
                unfocusedContainerColor = HomesharingTheme.colors.white,
                errorContainerColor = HomesharingTheme.colors.white,
                focusedIndicatorColor = HomesharingTheme.colors.black,
                unfocusedIndicatorColor = HomesharingTheme.colors.black,
                errorIndicatorColor = HomesharingTheme.colors.black,
                focusedLabelColor = HomesharingTheme.colors.black,
                unfocusedLabelColor = HomesharingTheme.colors.black,
                errorLabelColor = HomesharingTheme.colors.black,
            ),
        ),
    )
}

@Preview
@Composable
private fun PreviewHomesharingDatePicker() {
    val currentDate = remember {
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
    }
    val currentYear = remember(currentDate) { currentDate.year }

    val availableDates = remember {
        object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val day = Instant.fromEpochMilliseconds(utcTimeMillis)
                    .toLocalDateTime(TimeZone.UTC)
                    .date

                return day >= currentDate
            }

            override fun isSelectableYear(year: Int): Boolean = year >= currentYear
        }
    }

    val state = rememberDateRangePickerState(
        yearRange = currentYear..currentYear + 30,
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = availableDates,
    )
    HomesharingDatePicker(
        datePickerState = state
    )
}