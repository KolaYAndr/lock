package org.homesharing.lockbox.presentation.uikit.composables.core

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun LockBoxCircularProgress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        color = LockBoxTheme.colors.green500,
        strokeWidth = 4.dp,
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewCircle() {
    LockBoxTheme {
        LockBoxCircularProgress()
    }
}
