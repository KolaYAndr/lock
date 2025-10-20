package org.homesharing.lockbox.presentation.general.composables.login_top_rows

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.max
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun SettingsTopRowText() {
    val displayCutout = WindowInsets.displayCutout.asPaddingValues()
    val systemBar = WindowInsets.statusBars.asPaddingValues()
    val topPadding = max(displayCutout.calculateTopPadding(), systemBar.calculateTopPadding())
    Text(
        text = stringResource(R.string.settings),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey50,
        modifier = Modifier.padding(top = topPadding)
    )
}
