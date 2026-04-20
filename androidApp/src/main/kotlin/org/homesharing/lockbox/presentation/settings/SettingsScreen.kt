package org.homesharing.lockbox.presentation.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.navigation.routes.Route
import org.homesharing.lockbox.presentation.settings.viewmodel.SettingsUiState
import org.homesharing.lockbox.presentation.settings.viewmodel.SettingsViewModel
import org.homesharing.lockbox.presentation.uikit.composables.HelpModalSheet
import org.homesharing.lockbox.presentation.uikit.composables.IconTextArrow
import org.homesharing.lockbox.presentation.uikit.composables.NetworkImage
import org.homesharing.lockbox.presentation.uikit.composables.RoundText
import org.homesharing.lockbox.presentation.uikit.composables.TopAppBarSpacer
import org.homesharing.lockbox.presentation.uikit.composables.core.LockBoxPullToRefresh
import org.homesharing.lockbox.presentation.uikit.composables.core.LockboxHorizontalDivider
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxTextIconButton
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.BasicDialogContent
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxDialog
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxGroup
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingsScreen(
    onNavigate: (Route) -> Unit,
    settingsViewModel: SettingsViewModel = koinViewModel(),

    ) {
    val settingsState by settingsViewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreen(
        settingsUiState = settingsState,
        onLogOut = settingsViewModel::logOut,
        onRefresh = settingsViewModel::refreshAccountInfo,
        onNavigate = onNavigate,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
    settingsUiState: SettingsUiState,
    onNavigate: (Route) -> Unit,
    onLogOut: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier,
) {
    var showExitDialog by remember { mutableStateOf(false) }
    var showHelpModal by remember { mutableStateOf(false) }

    LockBoxPullToRefresh(
        modifier = modifier,
        onRefresh = onRefresh,
        isRefreshing = settingsUiState.isRefreshing,
        state = rememberPullToRefreshState()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LockBoxTheme.colors.grey950),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                bottom = 16.dp,
                top = 16.dp
            )
        ) {
            item {
                TopAppBarSpacer()
            }

            item {
                if (settingsUiState.imageLink.isNotBlank()) {
                    NetworkImage(url = settingsUiState.imageLink)
                } else {
                    RoundText(text = settingsUiState.fullName)
                }
            }

            item {
                AnimatedVisibility(
                    visible = settingsUiState.fullName.isNotEmpty(),
                    enter = expandVertically()
                ) {
                    Text(
                        text = settingsUiState.fullName,
                        style = LockBoxTheme.typography.titleMedium,
                        color = LockBoxTheme.colors.grey50,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = settingsUiState.fullName.isNotEmpty(),
                    enter = expandVertically()
                ) {
                    Text(
                        text = settingsUiState.email,
                        style = LockBoxTheme.typography.subHeadlineMedium,
                        color = LockBoxTheme.colors.grey500
                    )
                }
            }

            item {
                LockboxGroup(
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
                            LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }

            item {
                LockboxGroup(
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
                    LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())

                    IconTextArrow(
                        text = stringResource(R.string.help),
                        painter = painterResource(R.drawable.headset),
                        onClick = { showHelpModal = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                LockboxTextIconButton(
                    text = stringResource(R.string.log_out),
                    icon = painterResource(R.drawable.log_out),
                    onClick = { showExitDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                )
            }
        }
    }

    if (showExitDialog) {
        ExitDialog(
            onDismiss = { showExitDialog = false },
            onLogOut = onLogOut,
        )
    }

    if (showHelpModal) {
        HelpModalSheet(
            onDismiss = { showHelpModal = false },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ExitDialog(
    onDismiss: () -> Unit,
    onLogOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LockboxDialog(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        BasicDialogContent(
            title = stringResource(R.string.log_out_question),
            onDismiss = onDismiss,
            onDangerButtonAction = {
                onLogOut()
                onDismiss()
            },
            dangerButtonText = stringResource(R.string.log_out)
        )
    }
}

@Preview
@Composable
private fun PreviewSettings() {
    LockBoxTheme {
        val settingsUiState = SettingsUiState()
        SettingsScreen(settingsUiState, {}, {}, {}, Modifier.fillMaxSize())
    }
}
