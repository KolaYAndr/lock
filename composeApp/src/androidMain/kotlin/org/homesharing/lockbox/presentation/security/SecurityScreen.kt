package org.homesharing.lockbox.presentation.security

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.forgot_password.ForgotPasswordCard
import org.homesharing.lockbox.presentation.forgot_password.ForgotPasswordViewModel
import org.koin.androidx.compose.koinViewModel
import presentation.composables.TitledTopBar
import presentation.composables.general.BasicBottomSheet
import presentation.composables.general.BasicButton
import presentation.theme.LockBoxTheme

@Composable
fun SecurityScreen(
    securityUiState: SecurityUiState,
    onNavBack: () -> Unit,
    onToActiveSessions: () -> Unit,
    onBackToChangePassword: () -> Unit,
    onPasswordSet: () -> Unit,
    onReady: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitledTopBar(
            title = when (securityUiState) {
                is SecurityUiState.ChangePassword ->
                    stringResource(R.string.security)

                SecurityUiState.PasswordChanged -> stringResource(R.string.change_password)
            },
            onNavigate = when (securityUiState) {
                SecurityUiState.ChangePassword ->
                    onNavBack

                SecurityUiState.PasswordChanged ->
                    onBackToChangePassword.also { showDialog = false }
            },
            modifier = Modifier.fillMaxWidth()
        )

        when (securityUiState) {
            is SecurityUiState.ChangePassword -> {
                ChangePasswordScreen(
                    onShowDialog = { showDialog = true },
                    onToActiveSessions = onToActiveSessions,
                    showDialog = showDialog,
                    onHideDialog = { showDialog = false },
                    onPasswordSet = onPasswordSet
                )
            }

            SecurityUiState.PasswordChanged -> {
                PasswordChangedScreen(onReady = onReady)
            }
        }
    }
}

@Composable
private fun PasswordChangedScreen(onReady: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.circle_check),
            tint = LockBoxTheme.colors.green600,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = stringResource(R.string.password_changed),
            color = LockBoxTheme.colors.grey100,
            style = LockBoxTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp)
        )

        BasicButton(
            paddingValues = PaddingValues(10.dp),
            text = stringResource(R.string.ready),
            onClick = { onReady() }
        )
    }
}

@Composable
private fun ChangePasswordScreen(
    onShowDialog: () -> Unit,
    onToActiveSessions: () -> Unit,
    showDialog: Boolean,
    onHideDialog: () -> Unit,
    onPasswordSet: () -> Unit
) {
    SecurityElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .padding(horizontal = 16.dp),
        onClick = onShowDialog,
        title = stringResource(R.string.change_password)
    )

    SecurityElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp),
        title = stringResource(R.string.active_sessions),
        onClick = onToActiveSessions
    )

    if (showDialog) {
        BasicBottomSheet(
            onDismissRequest = onHideDialog::invoke
        ) {
            val forgotPasswordViewModel =
                koinViewModel<ForgotPasswordViewModel>()
            val forgotPasswordState =
                forgotPasswordViewModel.uiState.collectAsStateWithLifecycle()
            ForgotPasswordCard(
                forgotPasswordUiState = forgotPasswordState.value,
                title = stringResource(R.string.change_password),
                onLastBackPress = onHideDialog::invoke,
                onDismiss = onHideDialog::invoke,
                onEmailChange = forgotPasswordViewModel::onChangeEmail,
                onConfirmEmail = forgotPasswordViewModel::confirmEmail,
                onToStep = forgotPasswordViewModel::toStep,
                onPasswordChange = forgotPasswordViewModel::onChangePassword,
                onPasswordRepeatedChange = forgotPasswordViewModel::onChangePasswordRepeat,
                onPasswordSeenChange = forgotPasswordViewModel::onPasswordSeenChange,
                onPasswordRepeatedSeenChange = forgotPasswordViewModel::onPasswordRepeatedSeenChange,
                onSetPassword = {
                    forgotPasswordViewModel.setPassword()
                    onPasswordSet()
                }
            )
        }
    }
}

@Composable
private fun SecurityElement(title: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(
                color = LockBoxTheme.colors.grey900,
                shape = LockBoxTheme.shape.roundedCornerShape8
            )
            .border(
                width = 0.5.dp,
                color = LockBoxTheme.colors.grey800,
                shape = LockBoxTheme.shape.roundedCornerShape8
            )
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = LockBoxTheme.typography.headlineLarge,
            color = LockBoxTheme.colors.white,
            modifier = Modifier
                .padding(vertical = 14.dp)
                .padding(start = 16.dp)
        )

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            tint = LockBoxTheme.colors.grey500,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .padding(end = 16.dp),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PreviewSecurityScreenChangePassword() {
    LockBoxTheme {
        val state = SecurityUiState.ChangePassword
        SecurityScreen(
            securityUiState = state,
            {}, {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun PreviewSecurityScreenPasswordChanged() {
    LockBoxTheme {
        val state = SecurityUiState.PasswordChanged
        SecurityScreen(
            securityUiState = state,
            {}, {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
