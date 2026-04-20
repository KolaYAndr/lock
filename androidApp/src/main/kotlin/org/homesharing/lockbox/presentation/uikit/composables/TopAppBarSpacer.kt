package org.homesharing.lockbox.presentation.uikit.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun TopAppBarSpacer() {
    val topAppBarHeight = TopAppBarDefaults.TopAppBarExpandedHeight
    val topInsets =
        WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues()
            .calculateTopPadding()

    Spacer(modifier = Modifier.height(topAppBarHeight + topInsets))
}

@Preview
@Composable
private fun PreviewTopAppBarSpacer() {
    LockBoxTheme {
        TopAppBarSpacer()
    }
}