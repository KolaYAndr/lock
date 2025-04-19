package org.homesharing.lockbox.presentation.snackbar_controller

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
fun LockBoxSnackbar(snackbarData: SnackbarData, modifier: Modifier = Modifier) {
    Snackbar(
        modifier = modifier,
        containerColor = LockBoxTheme.colors.grey800,
        shape = LockBoxTheme.shape.roundedCornerShape8,
        actionContentColor = LockBoxTheme.colors.grey500,
        actionColor = LockBoxTheme.colors.green500,
        contentColor = LockBoxTheme.colors.grey100,
        dismissActionContentColor = LockBoxTheme.colors.grey500,
        snackbarData = snackbarData
    )
}
