package presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.general.BasicHorizontalDivider
import presentation.theme.LockBoxTheme

@Composable
fun TitledTopBar(
    title: String,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigate
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                    tint = LockBoxTheme.colors.grey500
                )
            }

            Text(
                text = title,
                style = LockBoxTheme.typography.titleSmall,
                color = LockBoxTheme.colors.grey100,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
            )
        }
        BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun PreviewTopBar() {
    LockBoxTheme {
        TitledTopBar(
            title = stringResource(R.string.account),
            onNavigate = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
