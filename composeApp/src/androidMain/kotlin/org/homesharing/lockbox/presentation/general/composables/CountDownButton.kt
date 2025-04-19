package org.homesharing.lockbox.presentation.general.composables

import android.os.CountDownTimer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.composables.basic.BasicButton
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme
import java.util.concurrent.TimeUnit

private val countDownInterval = TimeUnit.SECONDS.toMillis(1)
private val standardTime = TimeUnit.MINUTES.toMillis(1)

@Composable
fun CountDownButton(
    onClick: () -> Unit,
    startingTime: Long = standardTime,
    modifier: Modifier = Modifier,
) {
    var isEnabled by remember { mutableStateOf(false) }
    var timeData by remember { mutableLongStateOf(startingTime) }
    val timer = remember {
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

    val locale = ConfigurationCompat.getLocales(LocalConfiguration.current)[0]
    BasicButton(
        text = if (timeData > 0) stringResource(
            R.string.before_sending_one_more_time,
            String.format(locale = locale, format = "%02d", timeData / 60_000),
            String.format(locale = locale, format = "%02d", timeData % 60_000 / 1000)
        ) else stringResource(R.string.send_one_more_time),
        onClick = {
            onClick()
            timer.start()
            isEnabled = false
        },
        paddingValues = PaddingValues(vertical = 12.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = LockBoxTheme.colors.grey800,
            disabledContentColor = LockBoxTheme.colors.grey700,
            containerColor = LockBoxTheme.colors.grey800,
            contentColor = LockBoxTheme.colors.grey100
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewCountDown() {
    LockBoxTheme {
        CountDownButton({}, modifier = Modifier.fillMaxWidth())
    }
}
