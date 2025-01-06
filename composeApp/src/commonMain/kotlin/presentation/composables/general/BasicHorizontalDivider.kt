package presentation.composables.general

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.theme.LockBoxTheme

@Composable
fun BasicHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 0.5.dp,
        color = LockBoxTheme.colors.grey800
    )
}

@Preview
@Composable
fun PreviewDivider() {
    LockBoxTheme {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
}
