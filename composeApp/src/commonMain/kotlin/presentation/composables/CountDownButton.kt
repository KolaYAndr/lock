package presentation.composables

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.theme.LockBoxTheme
import java.util.concurrent.TimeUnit

private const val countDownInterval = 1_000L
private val standardTime = TimeUnit.SECONDS.toMillis(3)
//private val standardTime = TimeUnit.MINUTES.toMillis(1)

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

    Button(
        onClick = {
            onClick()
            timer.start()
            isEnabled = false
        },
        contentPadding = PaddingValues(vertical = 12.dp),
        enabled = isEnabled,
        shape = LockBoxTheme.shape.roundedCornerShape8,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = LockBoxTheme.colors.grey800,
            disabledContentColor = LockBoxTheme.colors.grey700,
            containerColor = LockBoxTheme.colors.grey800,
            contentColor = LockBoxTheme.colors.grey100
        ),
        modifier = modifier
    ) {
        Text(
            text = if (timeData > 0) stringResource(
                R.string.before_sending_one_more_time,
                timeData / 60_000,
                timeData / 1000
            ) else stringResource(R.string.send_one_more_time),
            style = LockBoxTheme.typography.headlineLarge
        )
    }
}