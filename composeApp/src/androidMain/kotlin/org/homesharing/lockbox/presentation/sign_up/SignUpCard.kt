package org.homesharing.lockbox.presentation.sign_up

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.R
import org.homesharing.lockbox.downloadPrivacyPolicy
import org.homesharing.lockbox.downloadUserAgreement
import org.homesharing.lockbox.presentation.animation.backwardAnimation
import org.homesharing.lockbox.presentation.animation.forwardAnimation
import presentation.composables.CardTopRow
import presentation.composables.general.BasicCard

private const val TOTAL_STEPS = 3

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
    onCheckConfirmation: suspend () -> Unit,
    onCreate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = signUpUiState.signUpStep,
        transitionSpec = {
            if (targetState.index > initialState.index) {
                forwardAnimation
            } else {
                backwardAnimation
            }
        }
    ) { targetStep ->
        BasicCard(modifier = modifier) {
            val context = LocalContext.current
            CardTopRow(
                onBackPressed = {
                    when (targetStep) {
                        SignUpStep.EmailInput -> onSignIn()
                        SignUpStep.PasswordInput -> onToStep(SignUpStep.EmailInput)
                        SignUpStep.EmailConfirmation -> onToStep(SignUpStep.PasswordInput)
                    }
                },
                onDismiss = { onDismiss() },
                titleText = stringResource(R.string.register),
                stepNumber = targetStep.index,
                totalSteps = TOTAL_STEPS,
                modifier = Modifier.fillMaxWidth()
            )
            when (targetStep) {
                SignUpStep.EmailInput -> {
                    EmailInputStep(
                        onSignIn = onSignIn,
                        toNextStep = { onToStep(SignUpStep.PasswordInput) },
                        email = signUpUiState.email,
                        emailError = signUpUiState.emailError,
                        onEmailChange = onEmailChange,
                        onDownload = {
                            downloadUserAgreement(context)
                            downloadPrivacyPolicy(context)
                        }
                    )
                }

                SignUpStep.PasswordInput -> {
                    PasswordInputStep(
                        password = signUpUiState.password,
                        onPasswordChange = onPasswordChange,
                        isPasswordSeen = signUpUiState.isPasswordSeen,
                        onPasswordRepeatedSeenChange = onPasswordRepeatedSeenChange,
                        passwordRepeated = signUpUiState.passwordRepeated,
                        passwordRepeatedError = signUpUiState.passwordRepeatedError,
                        onPasswordRepeatedChange = onPasswordRepeatedChange,
                        isPasswordRepeatedSeen = signUpUiState.isPasswordRepeatedSeen,
                        onPasswordSeenChanged = onPasswordSeenChange,
                        onCreate = onCreate,
                    )
                }

                SignUpStep.EmailConfirmation -> {
                    EmailConfirmationStep(
                        email = signUpUiState.email,
                        onConfirmEmail = onConfirmEmail,
                        onChangeEmailClick = { onToStep(SignUpStep.EmailInput) },
                        onCheckConfirmation = onCheckConfirmation
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSingUpEmailInput() {
    val state = SignUpUiState()
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}

@Preview
@Composable
private fun PreviewSingUpEmailConfirmation() {
    val state = SignUpUiState(signUpStep = SignUpStep.EmailConfirmation)
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}

@Preview
@Composable
private fun PreviewSingUpPasswordInput() {
    val state = SignUpUiState(signUpStep = SignUpStep.PasswordInput)
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}
