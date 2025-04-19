package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
fun SettingsTopRowText() {
    Text(
        text = stringResource(R.string.settings),
        style = LockBoxTheme.typography.titleSmall,
        color = LockBoxTheme.colors.grey100,
        modifier = Modifier.padding(12.dp)
    )
}
