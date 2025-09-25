package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DockedDatePicker(
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
                cursorColor = LockBoxTheme.colors.constantWhite,
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewDockedDatePicker() {
    val currentTime = System.currentTimeMillis()
    val calendar = Calendar.getInstance().apply {
        timeInMillis = currentTime
    }
    val currentYear = calendar.get(Calendar.YEAR)
    LockBoxTheme {
        val endDatePickerState = rememberDatePickerState(
            yearRange = currentYear..currentYear + 100,
            initialDisplayMode = DisplayMode.Picker,
        )

        DockedDatePicker(endDatePickerState)
    }
}
