package org.homesharing.lockbox.presentation.forgot_password

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.downloadPrivacyPolicy
import org.homesharing.lockbox.downloadUserAgreement
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

        val context = LocalContext.current
        when (forgotPasswordUiState.step) {
            ForgotPasswordStep.EmailConfirmation -> {
                EmailConfirmationStep(
                    email = forgotPasswordUiState.email,
                    onConfirmEmail = onConfirmEmail,
                    onChangeEmailClick = { onToStep(ForgotPasswordStep.EmailInput) },
                    onCheckConfirmation = { }
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
                    emailError = forgotPasswordUiState.emailError,
                    onEmailChange = onEmailChange,
                    onDownloadPdf = {
                        downloadUserAgreement(context)
                        downloadPrivacyPolicy(context)
                    }
                )
            }

            ForgotPasswordStep.PasswordInput -> {
                PasswordInputStep(
                    password = forgotPasswordUiState.password,
                    onPasswordChange = onPasswordChange,
                    isPasswordSeen = forgotPasswordUiState.isPasswordSeen,
                    onPasswordRepeatedSeenChange = onPasswordSeenChange,
                    passwordRepeated = forgotPasswordUiState.passwordRepeated,
                    onPasswordRepeatedChange = onPasswordRepeatedChange,
                    isPasswordRepeatedSeen = forgotPasswordUiState.isPasswordRepeatedSeen,
                    onPasswordSeenChanged = onPasswordRepeatedSeenChange,
                    passwordRepeatedError = forgotPasswordUiState.passwordRepeatedError,
                    toNextStep = onSetPassword
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewForgotPasswordEmailInput() {
    val state = ForgotPasswordUiState()
    ForgotPasswordCard(state, "", {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewForgotPasswordPasswordInput() {
    val state = ForgotPasswordUiState(step = ForgotPasswordStep.PasswordInput)
    ForgotPasswordCard(state, "", {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewForgotPasswordEmailConfirmation() {
    val state = ForgotPasswordUiState(step = ForgotPasswordStep.EmailConfirmation)
    ForgotPasswordCard(state, "", {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}
