//package org.homesharing.lockbox.presentation.uikit.composables.core.pickers
//
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.TimePicker
//import androidx.compose.material3.TimePickerDefaults
//import androidx.compose.material3.TimePickerState
//import androidx.compose.material3.rememberTimePickerState
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//import java.util.Calendar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//internal fun LockboxTimePicker(
//    timePickerState: TimePickerState,
//    modifier: Modifier = Modifier
//) {
//    TimePicker(
//        state = timePickerState,
//        layoutType = TimePickerDefaults.layoutType(),
//        colors = TimePickerDefaults.colors(
//            clockDialColor = LockBoxTheme.colors.grey800,
//            clockDialSelectedContentColor = LockBoxTheme.colors.grey100,
//            clockDialUnselectedContentColor = LockBoxTheme.colors.grey100,
//            selectorColor = LockBoxTheme.colors.grey700,
//            containerColor = LockBoxTheme.colors.grey800,
//            periodSelectorBorderColor = LockBoxTheme.colors.grey50,
//            periodSelectorSelectedContainerColor = LockBoxTheme.colors.grey700,
//            periodSelectorUnselectedContainerColor = LockBoxTheme.colors.grey800,
//            periodSelectorSelectedContentColor = LockBoxTheme.colors.grey100,
//            periodSelectorUnselectedContentColor = LockBoxTheme.colors.grey100,
//            timeSelectorSelectedContainerColor = LockBoxTheme.colors.grey700,
//            timeSelectorUnselectedContainerColor = LockBoxTheme.colors.grey800,
//            timeSelectorSelectedContentColor = LockBoxTheme.colors.grey100,
//            timeSelectorUnselectedContentColor = LockBoxTheme.colors.grey100
//        ),
//        modifier = modifier
//    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//private fun PreviewTimePicker() {
//    LockBoxTheme {
//        val currentTime = Calendar.getInstance()
//        val timePickerState = rememberTimePickerState(
//            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
//            initialMinute = currentTime.get(Calendar.MINUTE),
//            is24Hour = true,
//        )
//        LockboxTimePicker(timePickerState)
//    }
//}
