package org.homesharing.lockbox.presentation.sign_up

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.sign_up.steps.EmailConfirmationStep
import org.homesharing.lockbox.presentation.sign_up.steps.EmailInputStep
import org.homesharing.lockbox.presentation.sign_up.steps.PasswordInputStep
import org.homesharing.lockbox.presentation.uikit.animation.backwardAnimation
import org.homesharing.lockbox.presentation.uikit.animation.forwardAnimation
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxCard
import org.homesharing.lockbox.presentation.uikit.composables.top_rows.CardTopRow
import org.homesharing.lockbox.presentation.uikit.downloadPrivacyPolicy
import org.homesharing.lockbox.presentation.uikit.downloadUserAgreement
import org.koin.androidx.compose.koinViewModel

private const val TOTAL_STEPS = 3

@Composable
internal fun SignUpCard(
    onSignIn: () -> Unit,
    onDismiss: () -> Unit,
    signUpViewModel: SignUpViewModel = koinViewModel(),
) {
    val signUpState by signUpViewModel.uiState.collectAsStateWithLifecycle()
    SignUpCard(
        signUpUiState = signUpState,
        onSignIn = onSignIn,
        onDismiss = onDismiss,
        onEmailChange = signUpViewModel::onChangeEmail,
        onConfirmEmail = signUpViewModel::sendEmailConfirmation,
        onToStep = signUpViewModel::toStep,
        onPasswordChange = signUpViewModel::onChangePassword,
        onPasswordRepeatedChange = signUpViewModel::onChangePasswordRepeat,
        onCheckConfirmation = signUpViewModel::checkIfConfirmed,
        onCreate = signUpViewModel::signUp
    )
}

@Composable
private fun SignUpCard(
    signUpUiState: SignUpUiState,
    onSignIn: () -> Unit,
    onDismiss: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordRepeatedChange: (String) -> Unit,
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
    ) { step ->
        LockboxCard(
            modifier = modifier
        ) {
            val context = LocalContext.current
            CardTopRow(
                onBackPressed = {
                    when (step) {
                        SignUpStep.EmailInput -> onSignIn()
                        SignUpStep.PasswordInput -> onToStep(SignUpStep.EmailInput)
                        SignUpStep.EmailConfirmation -> onToStep(SignUpStep.PasswordInput)
                    }
                },
                onDismiss = { onDismiss() },
                titleText = stringResource(R.string.register),
                stepNumber = step.index,
                totalSteps = TOTAL_STEPS,
                modifier = Modifier.fillMaxWidth()
            )
            when (step) {
                SignUpStep.EmailInput -> {
                    EmailInputStep(
                        onSignIn = onSignIn,
                        toNextStep = { onToStep(SignUpStep.PasswordInput) },
                        email = signUpUiState.email,
                        emailError = signUpUiState.emailError,
                        onEmailChange = onEmailChange,
                        onDownloadConditions = {
                            downloadUserAgreement(context)
                        },
                        onDownloadPolicy = {
                            downloadPrivacyPolicy(context)
                        }
                    )
                }

                SignUpStep.PasswordInput -> {
                    PasswordInputStep(
                        password = signUpUiState.password,
                        onPasswordChange = onPasswordChange,
                        passwordRepeated = signUpUiState.passwordRepeated,
                        passwordRepeatedError = signUpUiState.passwordRepeatedError,
                        onPasswordRepeatedChange = onPasswordRepeatedChange,
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
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {})
}

@Preview
@Composable
private fun PreviewSingUpEmailConfirmation() {
    val state = SignUpUiState(signUpStep = SignUpStep.EmailConfirmation)
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {})
}

@Preview
@Composable
private fun PreviewSingUpPasswordInput() {
    val state = SignUpUiState(signUpStep = SignUpStep.PasswordInput)
    SignUpCard(state, {}, {}, {}, {}, {}, {}, {}, {}, {})
}
