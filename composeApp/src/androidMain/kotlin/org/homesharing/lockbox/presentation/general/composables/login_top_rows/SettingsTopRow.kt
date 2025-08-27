package org.homesharing.lockbox.presentation.general.composables.login_top_rows

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun SettingsTopRowText() {
    Text(
        text = stringResource(R.string.settings),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey50,
    )
}
