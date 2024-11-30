package presentation.composables.general

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import presentation.composables.LogInCard
import presentation.theme.LockBoxTheme

@Composable
fun BasicDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest::invoke,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewDialog() {
    LockBoxTheme {
        BasicDialog(onDismissRequest = {}) {
            LogInCard(onDismiss = {})
        }
    }
}
