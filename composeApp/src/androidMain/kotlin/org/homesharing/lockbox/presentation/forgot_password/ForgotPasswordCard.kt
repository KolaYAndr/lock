package org.homesharing.lockbox.presentation.forgot_password

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.sign_up.EmailConfirmationStep
import presentation.composables.CardTopRow
import presentation.composables.general.BasicCard

@Composable
fun ForgotPasswordCard(
    forgotPasswordUiState: ForgotPasswordUiState,
    onSignIn: () -> Unit,
    onDismiss: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordRepeatedChange: (String) -> Unit,
    onPasswordSeenChange: () -> Unit,
    onPasswordRepeatedSeenChange: () -> Unit,
    onConfirmEmail: () -> Unit,
    onToEmailInputStep: () -> Unit,
    onToEmailConfirmationStep: () -> Unit,
    onToPasswordInputStep: () -> Unit,
    onSetPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicCard(
        modifier = modifier
    ) {
        CardTopRow(
            onBackPressed = {
                when (forgotPasswordUiState.step) {
                    ForgotPasswordStep.EmailConfirmation -> onToEmailInputStep()
                    ForgotPasswordStep.EmailInput -> onSignIn()
                    ForgotPasswordStep.PasswordInput -> onToEmailConfirmationStep()
                }
            },
            onDismiss = { onDismiss() },
            titleText = stringResource(R.string.password_recovery),
            stepNumber = 0,
            totalSteps = 0,
            modifier = Modifier.fillMaxWidth()
        )

        when (forgotPasswordUiState.step) {
            ForgotPasswordStep.EmailConfirmation -> {
                EmailConfirmationStep(
                    email = forgotPasswordUiState.email,
                    onConfirmEmail = onConfirmEmail::invoke,
                    onChangeClick = onToEmailInputStep::invoke
                )
                onToPasswordInputStep()
            }
            ForgotPasswordStep.EmailInput -> {
                EmailInputStep(
                    onConfirmEmail = {
                        onConfirmEmail()
                        onToEmailConfirmationStep()
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