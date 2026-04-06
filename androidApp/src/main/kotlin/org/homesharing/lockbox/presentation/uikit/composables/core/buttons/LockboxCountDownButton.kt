package org.homesharing.lockbox.presentation.uikit.composables.core.buttons

import android.os.CountDownTimer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import java.util.concurrent.TimeUnit

private val countDownInterval = TimeUnit.SECONDS.toMillis(1)
private val defaultStartingTime = TimeUnit.MINUTES.toMillis(1)

@Composable
internal fun LockboxCountDownButton(
    onClick: () -> Unit,
    startingTime: Long = defaultStartingTime,
    modifier: Modifier = Modifier,
) {
    var isEnabled by remember { mutableStateOf(false) }
    var timeData by remember { mutableLongStateOf(startingTime) }
    val timer = retain {
        object : CountDownTimer(startingTime, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeData = millisUntilFinished
            }

            override fun onFinish() {
                timeData = 0L
                isEnabled = true
            }
        }
    }

    LaunchedEffect(timer) {
        timer.start()
    }

    LockboxButton(
        text = if (timeData > 0) stringResource(
            R.string.before_sending_one_more_time,
            String.format(format = "%02d", timeData / 60_000),
            String.format(format = "%02d", timeData % 60_000 / 1000)
        ) else stringResource(R.string.send_one_more_time),
        onClick = {
            onClick()
            timer.start()
            isEnabled = false
        },
        paddingValues = PaddingValues(vertical = 12.dp),
        enabled = isEnabled,
        style = LockboxButtonStyle.lighterGrey(),
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewLockboxCountDown() {
    LockBoxTheme {
        LockboxCountDownButton({}, modifier = Modifier.fillMaxWidth())
    }
}
