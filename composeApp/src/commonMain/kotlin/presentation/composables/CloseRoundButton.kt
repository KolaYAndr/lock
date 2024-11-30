package presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.theme.LockBoxTheme

@Composable
fun CloseRoundButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        modifier = modifier
            .size(24.dp),
        onClick = onClick::invoke,
        shape = LockBoxTheme.shape.circleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = LockBoxTheme.colors.grey800,
            contentColor = LockBoxTheme.colors.grey500
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.close),
            contentDescription = stringResource(R.string.close),
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
@Preview
fun PreviewCloseButton() {
    LockBoxTheme {
        CloseRoundButton(onClick = {})
    }
}
