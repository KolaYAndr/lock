package org.homesharing.lockbox.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
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
import org.homesharing.lockbox.presentation.forgot_password.ForgotPasswordCard
import org.homesharing.lockbox.presentation.forgot_password.ForgotPasswordViewModel
import org.homesharing.lockbox.presentation.sign_in.SignInCard
import org.homesharing.lockbox.presentation.sign_in.SignInViewModel
import org.homesharing.lockbox.presentation.sign_up.SignUpCard
import org.homesharing.lockbox.presentation.sign_up.SignUpViewModel
import org.koin.androidx.compose.koinViewModel
import presentation.composables.general.BasicBottomSheet
import presentation.composables.general.BasicButton
import presentation.theme.LockBoxTheme

@Composable
fun LoginScreen(
    state: LoginUiState,
    onToTypeAuth: (LoginType) -> Unit,
    onLoggedIn: () -> Unit,
    topRowContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoggedIn) { onLoggedIn() }

    var showModal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            topRowContent()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.log_in),
                contentDescription = null,
                tint = LockBoxTheme.colors.grey500,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = stringResource(R.string.log_in_your_profile),
                style = LockBoxTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 12.dp),
                color = LockBoxTheme.colors.grey100,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.start_using_today),
                style = LockBoxTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp),
                color = LockBoxTheme.colors.grey500,
                textAlign = TextAlign.Center
            )
            BasicButton(
                onClick = { showModal = true },
                text = stringResource(R.string.sing_in),
                paddingValues = PaddingValues(10.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .minimumInteractiveComponentSize()
            )

        }

        if (showModal) {
            BasicBottomSheet(
                onDismissRequest = { showModal = false },
            ) {
                when (state.loginType) {
                    LoginType.ForgotPassword -> {
                        val forgotPasswordViewModel = koinViewModel<ForgotPasswordViewModel>()
                        val forgotPasswordState =
                            forgotPasswordViewModel.uiState.collectAsStateWithLifecycle()

                        ForgotPasswordCard(
                            forgotPasswordUiState = forgotPasswordState.value,
                            title = stringResource(R.string.password_recovery),
                            onLastBackPress = { onToTypeAuth(LoginType.SignIn) },
                            onDismiss = { showModal = false },
                            onEmailChange = forgotPasswordViewModel::onChangeEmail,
                            onConfirmEmail = forgotPasswordViewModel::confirmEmail,
                            onToStep = forgotPasswordViewModel::toStep,
                            onPasswordChange = forgotPasswordViewModel::onChangePassword,
                            onPasswordRepeatedChange = forgotPasswordViewModel::onChangePasswordRepeat,
                            onPasswordSeenChange = forgotPasswordViewModel::onPasswordSeenChange,
                            onPasswordRepeatedSeenChange = forgotPasswordViewModel::onPasswordRepeatedSeenChange,
                            onSetPassword = {
                                forgotPasswordViewModel.setPassword()
                                onToTypeAuth(LoginType.SignIn)
                            }
                        )
                    }

                    LoginType.SignIn -> {
                        val signInViewModel = koinViewModel<SignInViewModel>()
                        val signInState = signInViewModel.uiState.collectAsStateWithLifecycle()

                        if (signInState.value.loggedIn) {
                            onLoggedIn()
                        } else SignInCard(
                            signInUiState = signInState.value,
                            onDismiss = { showModal = false },
                            onEmailChange = signInViewModel::editEmail,
                            onPasswordChange = signInViewModel::editPassword,
                            onPasswordSeenChange = signInViewModel::showPasswordToggle,
                            passwordError = signInState.value.passwordErrorMessage != null,
                            passwordErrorText = signInState.value.passwordErrorMessage ?: "",
                            emailError = signInState.value.emailErrorMessage != null,
                            emailErrorText = signInState.value.emailErrorMessage ?: "",
                            onToTypeAuth = onToTypeAuth,
                            onSignIn = signInViewModel::signIn
                        )
                    }

                    LoginType.SignUp -> {
                        val signUpViewModel = koinViewModel<SignUpViewModel>()
                        val signUpState = signUpViewModel.uiState.collectAsStateWithLifecycle()

                        SignUpCard(
                            signUpUiState = signUpState.value,
                            onSignIn = { onToTypeAuth(LoginType.SignIn) },
                            onDismiss = { showModal = false },
                            onEmailChange = signUpViewModel::onChangeEmail,
                            onConfirmEmail = signUpViewModel::confirmEmail,
                            onToStep = signUpViewModel::toStep,
                            onPasswordChange = signUpViewModel::onChangePassword,
                            onPasswordRepeatedChange = signUpViewModel::onChangePasswordRepeat,
                            onPasswordSeenChange = signUpViewModel::onPasswordSeenChange,
                            onPasswordRepeatedSeenChange = signUpViewModel::onPasswordRepeatedSeenChange,
                            onSetPassword = {
                                signUpViewModel.setPassword()
                                onToTypeAuth(LoginType.SignIn)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LockBoxTheme {
        val uiState = LoginUiState()
        LoginScreen(
            state = uiState,
            {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
