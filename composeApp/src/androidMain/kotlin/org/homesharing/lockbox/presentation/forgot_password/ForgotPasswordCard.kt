package org.homesharing.lockbox.presentation.forgot_password

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.sign_up.EmailConfirmationStep
import presentation.composables.CardTopRow
import presentation.composables.general.BasicCard

@Composable
fun ForgotPasswordCard(
    forgotPasswordUiState: ForgotPasswordUiState,
    title: String,
    onLastBackPress: () -> Unit,
    onDismiss: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordRepeatedChange: (String) -> Unit,
    onPasswordSeenChange: () -> Unit,
    onPasswordRepeatedSeenChange: () -> Unit,
    onConfirmEmail: () -> Unit,
    onToStep: (ForgotPasswordStep) -> Unit,
    onSetPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicCard(
        modifier = modifier
    ) {
        CardTopRow(
            onBackPressed = {
                when (forgotPasswordUiState.step) {
                    ForgotPasswordStep.EmailConfirmation -> onToStep(ForgotPasswordStep.EmailInput)
                    ForgotPasswordStep.EmailInput -> onLastBackPress()
                    ForgotPasswordStep.PasswordInput -> onToStep(ForgotPasswordStep.EmailConfirmation)
                }
            },
            onDismiss = { onDismiss() },
            titleText = title,
            stepNumber = 0,
            totalSteps = 0,
            modifier = Modifier.fillMaxWidth()
        )

        when (forgotPasswordUiState.step) {
            ForgotPasswordStep.EmailConfirmation -> {
                EmailConfirmationStep(
                    email = forgotPasswordUiState.email,
                    onConfirmEmail = onConfirmEmail::invoke,
                    onChangeClick = { onToStep(ForgotPasswordStep.EmailConfirmation) }
                )
               onToStep(ForgotPasswordStep.PasswordInput)
            }
            ForgotPasswordStep.EmailInput -> {
                EmailInputStep(
                    onConfirmEmail = {
                        onConfirmEmail()
                        onToStep(ForgotPasswordStep.EmailConfirmation)
                    },
                    email = forgotPasswordUiState.email,
                    onEmailChange = onEmailChange::invoke
                )
            }

            ForgotPasswordStep.PasswordInput -> {
                PasswordInputStep(
                    password = forgotPasswordUiState.password,
                    onPasswordChange = onPasswordChange::invoke,
                    isPasswordSeen = forgotPasswordUiState.isPasswordSeen,
                    onPasswordRepeatedSeenChange = onPasswordSeenChange::invoke,
                    passwordRepeated = forgotPasswordUiState.passwordRepeated,
                    onPasswordRepeatedChange = onPasswordRepeatedChange::invoke,
                    isPasswordRepeatedSeen = forgotPasswordUiState.isPasswordRepeatedSeen,
                    onPasswordSeenChanged = onPasswordRepeatedSeenChange::invoke,
                    onSetPassword = onSetPassword::invoke
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewForgotPassword() {
    val state = ForgotPasswordUiState()
    ForgotPasswordCard(state, "", {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}
