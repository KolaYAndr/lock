package org.homesharing.lockbox.presentation.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.CloseRoundButton
import presentation.composables.EmailInput
import presentation.composables.PasswordInput
import presentation.composables.PolicyAndConditions
import presentation.composables.general.BasicButton
import presentation.composables.general.BasicCard
import presentation.theme.LockBoxTheme


@Composable
fun SignInCard(
    signInUiState: SignInUiState,
    onDismiss: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    passwordError: Boolean,
    emailError: Boolean,
    passwordErrorText: String,
    emailErrorText: String,
    onPasswordSeenChange: (Boolean) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicCard(
        modifier = modifier
    ) {
        val emailInteractionSource = remember { MutableInteractionSource() }
        val passwordInteractionSource = remember { MutableInteractionSource() }
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        Box(modifier = Modifier.fillMaxWidth()) {
            CloseRoundButton(
                onClick = onDismiss::invoke,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 16.dp)
            )

            Icon(
                painter = painterResource(R.drawable.logo_horizontal),
                contentDescription = null,
                tint = LockBoxTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 12.dp)
            )
        }


        Text(
            text = stringResource(R.string.log_in_your_profile),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.white,
            modifier = Modifier.padding(top = 12.dp, start = 16.dp)
        )

        Text(
            text = stringResource(R.string.email),
            style = LockBoxTheme.typography.subHeadlineLarge,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.Start)
        )
        EmailInput(
            email = signInUiState.email,
            onValueChange = { onEmailChange(it) },
            onClearText = { onEmailChange("") },
            interactionSource = emailInteractionSource,
            isError = emailError,
            errorText = emailErrorText,
            keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                showKeyboardOnFocus = true
            ),
            modifier = Modifier.padding(top = 6.dp, start = 16.dp, end = 16.dp)
        )

        Text(
            text = stringResource(R.string.password),
            style = LockBoxTheme.typography.subHeadlineLarge,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.Start)
        )
        PasswordInput(
            password = signInUiState.password,
            onValueChange = { onPasswordChange(it) },
            onClearText = { onPasswordChange("") },
            interactionSource = passwordInteractionSource,
            isError = passwordError,
            errorText = passwordErrorText,
            isSeen = signInUiState.isPasswordSeen,
            onSeenChange = { onPasswordSeenChange(!signInUiState.isPasswordSeen) },
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true
            ),
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(top = 6.dp, start = 16.dp, end = 16.dp)
        )

        Text(
            text = stringResource(R.string.forgot_password),
            style = LockBoxTheme.typography.subHeadlineMedium,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.Start)
                .clickable { onForgotPassword() },
            textDecoration = TextDecoration.Underline
        )

        BasicButton(
            onClick = {},
            text = stringResource(R.string.sing_in),
            paddingValues = PaddingValues(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
        )

        PolicyAndConditions(
            modifier = Modifier
                .padding(top = 2.dp, start = 16.dp, end = 16.dp)
                .clickable {

                }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.no_account_question),
                color = LockBoxTheme.colors.grey500,
                style = LockBoxTheme.typography.subHeadlineMedium,
                modifier = Modifier.padding(vertical = 14.dp)
            )

            Text(
                text = stringResource(R.string.register),
                color = LockBoxTheme.colors.grey100,
                style = LockBoxTheme.typography.subHeadlineLarge,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .clickable { onSignUp() }
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignIn() {
    LockBoxTheme {
        val state = SignInUiState()
        SignInCard(
            signInUiState = state,
            {}, {}, {}, false, false, "", "", {}, {}, {}
        )
    }
}
