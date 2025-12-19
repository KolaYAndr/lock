package org.homesharing.lockbox.presentation.uikit.composables.top_rows

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun SettingsTopRowText() {
    Text(
        text = stringResource(R.string.settings),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey50,
    )
}
