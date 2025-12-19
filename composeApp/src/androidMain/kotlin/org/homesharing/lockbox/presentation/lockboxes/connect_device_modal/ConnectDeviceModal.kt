package org.homesharing.lockbox.presentation.lockboxes.connect_device_modal

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.composables.TitledTopWithCloseAndBackButtons
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.BasicButtonStyle
import org.homesharing.lockbox.presentation.uikit.composables.core.LockboxHorizontalDivider
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxModal
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ConnectDeviceModal(
    modifier: Modifier,
    onDismiss: () -> Unit,
    connectDeviceViewModel: ConnectDeviceModalViewModel = koinViewModel(),
) {
    ConnectDeviceModal(
        onDismiss = onDismiss,
        onCreateTestDevice = {
            connectDeviceViewModel.createTestDevice()
            onDismiss()
        },
        modifier = modifier
    )
}

@Composable
private fun ConnectDeviceModal(
    onDismiss: () -> Unit,
    onCreateTestDevice: () -> Unit,
    modifier: Modifier,
) {
    val settingsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    LockboxModal(
        modifier = modifier,
        onDismiss = onDismiss,
    ) {
        TitledTopWithCloseAndBackButtons(
            title = stringResource(R.string.device_connection),
            onDismiss = onDismiss,
            modifier = Modifier.fillMaxWidth()
        )

        LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.router),
                contentDescription = null,
                tint = LockBoxTheme.colors.grey500,
                modifier = Modifier.size(48.dp),
            )

            Text(
                text = stringResource(R.string.connect_to_lockbox_network),
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.grey100,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = stringResource(R.string.go_to_wifi_settings_to_connect),
                style = LockBoxTheme.typography.bodyMedium,
                color = LockBoxTheme.colors.grey500,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            LockboxButton(
                onClick = { settingsLauncher.launch(Intent(Settings.ACTION_WIFI_SETTINGS)) },
                text = stringResource(R.string.open_wifi_settings),
                paddingValues = PaddingValues(10.dp),
                modifier = Modifier.padding(top = 16.dp)
            )

            LockboxButton(
                onClick = onDismiss,
                text = stringResource(R.string.cancel),
                modifier = Modifier.padding(top = 12.dp),
                paddingValues = PaddingValues(horizontal = 12.dp),
                style = BasicButtonStyle.grey()
            )
        }

        LockboxButton(
            onClick = onCreateTestDevice,
            text = stringResource(R.string.add_virtual_device),
            modifier = Modifier
                .padding(bottom = 12.dp)
                .align(Alignment.CenterHorizontally),
            paddingValues = PaddingValues(horizontal = 12.dp),
            style = BasicButtonStyle.grey()
        )
    }
}
