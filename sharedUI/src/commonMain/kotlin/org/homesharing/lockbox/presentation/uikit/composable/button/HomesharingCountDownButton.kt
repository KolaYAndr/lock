package org.homesharing.lockbox.presentation.uikit.composable.button

//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableLongStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.retain.retain
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import org.jetbrains.compose.resources.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import lockbox.sharedui.generated.resources.Res
//import lockbox.sharedui.generated.resources.send_code_again
//import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
//import java.text.SimpleDateFormat
//import java.util.Locale
//import java.util.concurrent.TimeUnit

//private val countDownInterval = TimeUnit.SECONDS.toMillis(1)
//private val defaultStartingTime = TimeUnit.MINUTES.toMillis(1)

//@Composable
//internal fun HomesharingCountDownButton(
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    startingTime: Long = defaultStartingTime,
//) {
//    var isEnabled by remember { mutableStateOf(false) }
//    var timeData by remember { mutableLongStateOf(startingTime) }
//    val formatter = remember { SimpleDateFormat("H:mm", Locale.getDefault()) }
//    val timer = retain {
//        object : CountDownTimer(startingTime, countDownInterval) {
//            override fun onTick(millisUntilFinished: Long) {
//                timeData = millisUntilFinished
//            }
//
//            override fun onFinish() {
//                timeData = 0L
//                isEnabled = true
//            }
//        }
//    }
//
//    LaunchedEffect(timer) {
//        onClick()
//        timer.start()
//    }
//
//    HomesharingButton(
//        modifier = modifier,
//        title = stringResource(Res.string.send_code_again),
//        subtitle = if (timeData > 0) {
//            formatter.format(timeData)
//        } else {
//            null
//        },
//        enabled = isEnabled,
//        onClick = onClick,
//        style = HomesharingButtonDefaults.secondary()
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun PreviewCountDownButton() {
//    HomesharingTheme {
//        HomesharingCountDownButton(
//            onClick = { }
//        )
//    }
//}