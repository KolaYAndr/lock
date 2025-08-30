package org.homesharing.lockbox.presentation.general.composables.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.general.composables.LockBoxCircularProgress
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun LoadingScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LockBoxCircularProgress()
    }
}

@Preview
@Composable
private fun PreviewLoadingScreen() {
    LockBoxTheme {
        LoadingScreenContent(modifier = Modifier.fillMaxSize())
    }
}
