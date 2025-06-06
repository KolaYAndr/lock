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
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.downloadPrivacyPolicy
import org.homesharing.lockbox.presentation.general.downloadlServiceConditions
import org.homesharing.lockbox.presentation.login.LoginType
import org.homesharing.lockbox.presentation.general.composables.CloseRoundButton
import org.homesharing.lockbox.presentation.general.composables.inputs.EmailInput
import org.homesharing.lockbox.presentation.general.composables.inputs.PasswordInput
import org.homesharing.lockbox.presentation.general.composables.PolicyAndConditions
import org.homesharing.lockbox.presentation.general.composables.basic.BasicButton
import org.homesharing.lockbox.presentation.general.composables.basic.BasicCard
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun SignInCard(
    signInUiState: SignInUiState,
    onDismiss: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    passwordError: Boolean,
    emailError: Boolean,
    passwordErrorText: String,
    emailErrorText: String,
    onPasswordSeenChange: () -> Unit,
    onToTypeAuth: (LoginType) -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicCard(
        modifier = modifier
    ) {
        val emailInteractionSource = remember { MutableInteractionSource() }
        val passwordInteractionSource = remember { MutableInteractionSource() }
        val passwordFocusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) { focusRequester.requestFocus() }

        Box(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(R.drawable.logo_horizontal),
                contentDescription = null,
                tint = LockBoxTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 12.dp)
            )

            CloseRoundButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 16.dp)
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
            keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                showKeyboardOnFocus = true
            ),
            modifier = Modifier
                .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                .focusRequester(focusRequester)
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
            onSeenChange = onPasswordSeenChange,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onSignIn()
                }
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true
            ),
            modifier = Modifier
                .focusRequester(passwordFocusRequester)
                .padding(top = 6.dp, start = 16.dp, end = 16.dp)
        )

        val uriHandler = LocalUriHandler.current
        Text(
            text = stringResource(R.string.forgot_password),
            style = LockBoxTheme.typography.subHeadlineMedium,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.Start)
                .clickable { uriHandler.openUri("https://api.lockboxes.ru:8443/realms/lockbox/login-actions/reset-credentials?client_id=public-client") },
            textDecoration = TextDecoration.Underline
        )

        BasicButton(
            onClick = { onSignIn() },
            text = stringResource(R.string.sing_in),
            paddingValues = PaddingValues(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
        )

        val context = LocalContext.current
        PolicyAndConditions(
            onConditionsClick = {
                downloadlServiceConditions(context)
            },
            onPolicyClick = {
                downloadPrivacyPolicy(context)
            },
            modifier = Modifier.padding(top = 2.dp, start = 16.dp, end = 16.dp)
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
                    .minimumInteractiveComponentSize()
                    .clickable { onToTypeAuth(LoginType.SignUp) }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSignIn() {
    LockBoxTheme {
        val state = SignInUiState()
        SignInCard(
            signInUiState = state,
            onDismiss = {},
            onPasswordChange = {},
            onEmailChange = {},
            passwordError = false,
            emailError = false,
            passwordErrorText = "",
            emailErrorText = "",
            onPasswordSeenChange = {},
            onToTypeAuth = {},
            onSignIn = {}
        )
    }
}
