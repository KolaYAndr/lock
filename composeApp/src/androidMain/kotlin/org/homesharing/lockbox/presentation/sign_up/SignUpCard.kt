package org.homesharing.lockbox.presentation.sign_up

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.R
import presentation.composables.CardTopRow
import presentation.composables.general.BasicCard

@Composable
fun SignUpCard(
    signUpUiState: SignUpUiState,
    onSignIn: () -> Unit,
    onDismiss: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordRepeatedChange: (String) -> Unit,
    onPasswordSeenChange: () -> Unit,
    onPasswordRepeatedSeenChange: () -> Unit,
    onConfirmEmail: () -> Unit,
    onToStep: (SignUpStep) -> Unit,
    onSetPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicCard(
        modifier = modifier
    ) {
        CardTopRow(
            onBackPressed = {
                when (signUpUiState.signUpStep) {
                    SignUpStep.EmailConfirmation -> onToStep(SignUpStep.EmailInput)
                    SignUpStep.EmailInput -> onSignIn()
                    SignUpStep.PasswordInput -> onToStep(SignUpStep.EmailInput) // onToStep(SignUpStep.EmailConfirmation)
                }
            },
            onDismiss = { onDismiss() },
            titleText = stringResource(R.string.register),
            stepNumber = when (signUpUiState.signUpStep) {
                SignUpStep.EmailInput -> 1
                SignUpStep.EmailConfirmation -> 2
                SignUpStep.PasswordInput -> 3
            },
            totalSteps = signUpUiState.totalSteps,
            modifier = Modifier.fillMaxWidth()
        )

        when (signUpUiState.signUpStep) {
            SignUpStep.EmailConfirmation -> {
                EmailConfirmationStep(
                    email = signUpUiState.email,
                    onConfirmEmail = onConfirmEmail::invoke,
                    onChangeClick = { onToStep(SignUpStep.EmailConfirmation) }
                )
                onToStep(SignUpStep.PasswordInput)
            }
            SignUpStep.EmailInput -> {
                EmailInputStep(
                    onSignIn = onSignIn::invoke,
                    onConfirmEmail = {
                        onConfirmEmail()
                        onToStep(SignUpStep.EmailConfirmation)
                    },
                    email = signUpUiState.email,
                    emailError = signUpUiState.emailError,
                    onEmailChange = onEmailChange::invoke
                )
            }

            SignUpStep.PasswordInput -> {
                PasswordInputStep(
                    password = signUpUiState.password,
                    onPasswordChange = onPasswordChange::invoke,
                    isPasswordSeen = signUpUiState.isPasswordSeen,
                    onPasswordRepeatedSeenChange = onPasswordRepeatedSeenChange::invoke,
                    passwordRepeated = signUpUiState.passwordRepeated,
                    passwordRepeatedError = signUpUiState.passwordRepeatedError,
                    onPasswordRepeatedChange = onPasswordRepeatedChange::invoke,
                    isPasswordRepeatedSeen = signUpUiState.isPasswordRepeatedSeen,
                    onPasswordSeenChanged = onPasswordSeenChange::invoke,
                    onSetPassword = onSetPassword::invoke
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSingUpEmailInput() {
    val state = SignUpUiState()
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewSingUpEmailConfirmation() {
    val state = SignUpUiState(signUpStep = SignUpStep.EmailConfirmation)
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewSingUpPasswordInput() {
    val state = SignUpUiState(signUpStep = SignUpStep.PasswordInput)
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}
