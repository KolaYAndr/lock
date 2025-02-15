package org.homesharing.lockbox.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.navigation.routes.SettingsRoutes
import presentation.composables.NetworkImage
import presentation.composables.RoundText
import presentation.composables.general.BasicButton
import presentation.composables.general.BasicDialog
import presentation.composables.general.BasicGroup
import presentation.composables.general.BasicHorizontalDivider
import presentation.theme.LockBoxTheme

@Composable
fun SettingsScreen(
    settingsUiState: SettingsUiState,
    onNavigate: (SettingsRoutes) -> Unit,
    onLogOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showDialog by remember { mutableStateOf(false) }
        Text(
            text = stringResource(R.string.settings),
            style = LockBoxTheme.typography.titleSmall,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .align(Alignment.Start)
        )

        if (settingsUiState.imageLink.isNotEmpty()) {
            NetworkImage(model = settingsUiState.imageLink)
        } else RoundText(text = settingsUiState.fullName)

        if (settingsUiState.fullName.isNotEmpty())
            Text(
                text = settingsUiState.fullName,
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.grey50,
                modifier = Modifier.padding(top = 12.dp)
            )

        Text(
            text = settingsUiState.email,
            style = LockBoxTheme.typography.subHeadlineMedium,
            color = LockBoxTheme.colors.grey500
        )

        val uriHandler = LocalUriHandler.current
        BasicGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            accountProperties.forEachIndexed { index, property ->
                SettingsProperty(
                    property = property,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (property.route != null) {
                        onNavigate(property.route)
                    } else {
                        uriHandler.openUri("t.me/@korolev_2512")
                    }
                }
                if (index != accountProperties.lastIndex) {
                    BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
            }
        }

        BasicGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            aboutProperties.forEachIndexed { index, property ->
                SettingsProperty(
                    property = property,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (property.route != null) {
                        onNavigate(property.route)
                    } else {
                        uriHandler.openUri("https://t.me/@korolev_2512")
                    }
                }
                if (index != accountProperties.lastIndex) {
                    BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .clickable { showDialog = true },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            Icon(
                painter = painterResource(R.drawable.log_out),
                tint = LockBoxTheme.colors.red600,
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.log_out),
                color = LockBoxTheme.colors.red600,
                style = LockBoxTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        if (showDialog) {
            BasicDialog(
                onDismiss = { showDialog = false }
            ) {
                Text(
                    text = stringResource(R.string.log_out_question),
                    style = LockBoxTheme.typography.titleMedium,
                    color = LockBoxTheme.colors.grey100
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = LockBoxTheme.typography.headlineMedium,
                        color = LockBoxTheme.colors.grey100,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable { showDialog = false }
                    )

                    BasicButton(
                        onClick = {
                            onLogOut()
                            showDialog = false
                        },
                        text = stringResource(R.string.log_out),
                        paddingValues = PaddingValues(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LockBoxTheme.colors.red600,
                            contentColor = LockBoxTheme.colors.grey100
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsProperty(
    property: SettingsProperty,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(property.icon),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey100,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = stringResource(property.title),
            style = LockBoxTheme.typography.headlineLarge,
            color = LockBoxTheme.colors.white,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 14.dp)
        )
        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey100,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Preview
@Composable
fun PreviewSettings() {
    LockBoxTheme {
        val settingsUiState = SettingsUiState()
        SettingsScreen(settingsUiState, {}, {}, Modifier.fillMaxSize())
    }
}
