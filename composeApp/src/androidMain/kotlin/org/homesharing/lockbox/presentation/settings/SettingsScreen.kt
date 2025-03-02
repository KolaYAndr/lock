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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.navigation.routes.SettingsRoutes
import presentation.composables.HelpModalSheet
import presentation.composables.IconTextArrow
import presentation.composables.NetworkImage
import presentation.composables.LockBoxPullToRefresh
import presentation.composables.RoundText
import presentation.composables.general.BasicButton
import presentation.composables.general.BasicDialog
import presentation.composables.general.BasicGroup
import presentation.composables.general.BasicHorizontalDivider
import presentation.theme.LockBoxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsUiState: SettingsUiState,
    onNavigate: (SettingsRoutes) -> Unit,
    onLogOut: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
    ) {
        var showExitDialog by remember { mutableStateOf(false) }
        var showHelpModal by remember { mutableStateOf(false) }
        Text(
            text = stringResource(R.string.settings),
            style = LockBoxTheme.typography.titleSmall,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .align(Alignment.Start)
        )

        LockBoxPullToRefresh(
            modifier = Modifier.fillMaxSize(),
            onRefresh = onRefresh,
            isRefreshing = settingsUiState.isRefreshing
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    if (settingsUiState.imageLink.isNotEmpty()) {
                        NetworkImage(url = settingsUiState.imageLink)
                    } else RoundText(text = settingsUiState.fullName)

                    if (settingsUiState.fullName.isNotEmpty())
                        Text(
                            text = settingsUiState.fullName,
                            style = LockBoxTheme.typography.titleMedium,
                            color = LockBoxTheme.colors.grey50,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                }

                item {
                    Text(
                        text = settingsUiState.email,
                        style = LockBoxTheme.typography.subHeadlineMedium,
                        color = LockBoxTheme.colors.grey500
                    )
                }

                item {
                    BasicGroup(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    ) {
                        accountProperties.forEachIndexed { index, property ->
                            IconTextArrow(
                                text = stringResource(property.title),
                                painter = painterResource(property.icon),
                                onClick = { onNavigate(property.route) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (index != accountProperties.lastIndex) {
                                BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }

                item {
                    BasicGroup(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    ) {
                        IconTextArrow(
                            text = stringResource(aboutProperty.title),
                            painter = painterResource(aboutProperty.icon),
                            onClick = { onNavigate(aboutProperty.route) },
                            modifier = Modifier.fillMaxWidth()

                        )
                        BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

                        IconTextArrow(
                            text = stringResource(R.string.help),
                            painter = painterResource(R.drawable.headset),
                            onClick = { showHelpModal = true },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                            .clickable { showExitDialog = true },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            12.dp,
                            Alignment.CenterHorizontally
                        )
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
                }
            }
        }

        if (showExitDialog) {
            ExitDialog(
                onDismiss = { showExitDialog = false },
                onLogOut = onLogOut
            )
        }

        if (showHelpModal) {
            HelpModalSheet(
                onDismiss = { showHelpModal = false },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ExitDialog(onDismiss: () -> Unit, onLogOut: () -> Unit) {
    BasicDialog(
        onDismiss = { onDismiss() }
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
                    .clickable { onDismiss() }
            )

            BasicButton(
                onClick = {
                    onLogOut()
                    onDismiss()
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

@Preview
@Composable
fun PreviewSettings() {
    LockBoxTheme {
        val settingsUiState = SettingsUiState()
        SettingsScreen(settingsUiState, {}, {}, {}, Modifier.fillMaxSize())
    }
}
