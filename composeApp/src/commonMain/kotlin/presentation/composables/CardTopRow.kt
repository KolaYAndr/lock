package presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.general.BasicHorizontalDivider
import presentation.theme.LockBoxTheme

@Composable
fun CardTopRow(
    onBackPressed: () -> Unit,
    onDismiss: () -> Unit,
    titleText: String,
    stepNumber: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackPressed() },
            modifier = Modifier.minimumInteractiveComponentSize()
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = null,
                tint = LockBoxTheme.colors.grey500
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(
                2.dp,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(
                text = titleText,
                style = LockBoxTheme.typography.titleSmall,
                color = LockBoxTheme.colors.grey100
            )
            if (totalSteps != 0) {
                Text(
                    text = stringResource(R.string.step_of, stepNumber, totalSteps),
                    style = LockBoxTheme.typography.subHeadlineMedium,
                    color = LockBoxTheme.colors.grey500
                )
            }
        }
        IconButton(
            onClick = { onDismiss() },
            modifier = Modifier.minimumInteractiveComponentSize()
        ) {
            Icon(
                painter = painterResource(R.drawable.close),
                contentDescription = null,
                tint = LockBoxTheme.colors.grey500
            )
        }
    }

    BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())
}