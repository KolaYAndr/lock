package org.homesharing.lockbox.presentation.sign_up

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.R
import presentation.composables.CardTopRow
import presentation.composables.general.BasicCard

private const val TOTAL_STEPS = 3
private const val STEP_ONE = 1
private const val STEP_TWO = 2
private const val STEP_THREE = 3

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
                SignUpStep.EmailInput -> STEP_ONE
                SignUpStep.EmailConfirmation -> STEP_TWO
                SignUpStep.PasswordInput -> STEP_THREE
            },
            totalSteps = TOTAL_STEPS,
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
