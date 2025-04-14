package presentation.composables.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import presentation.theme.LockBoxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss::invoke,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        modifier = modifier
            .padding(horizontal = 32.dp)
            .background(
                color = LockBoxTheme.colors.grey900,
                shape = LockBoxTheme.shape.roundedCornerShape8
            )
            .border(
                width = 0.5.dp,
                color = LockBoxTheme.colors.grey800,
                shape = LockBoxTheme.shape.roundedCornerShape8
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun PreviewDialog() {
    LockBoxTheme {
        BasicDialog(
            onDismiss = {},
            modifier = Modifier.padding(top = 12.dp)
        ) {
            BasicButton(
                {}, "",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
