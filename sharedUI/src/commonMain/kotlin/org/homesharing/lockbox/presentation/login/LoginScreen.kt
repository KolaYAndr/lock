package org.homesharing.lockbox.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.background
import lockbox.sharedui.generated.resources.check_in_online
import lockbox.sharedui.generated.resources.get_code
import lockbox.sharedui.generated.resources.ic_close
import lockbox.sharedui.generated.resources.ic_eye
import lockbox.sharedui.generated.resources.ic_eye_off
import lockbox.sharedui.generated.resources.lockbox_logo_horizontal
import lockbox.sharedui.generated.resources.or
import lockbox.sharedui.generated.resources.password_placeholder
import lockbox.sharedui.generated.resources.placeholder_email
import lockbox.sharedui.generated.resources.sign_in
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.divider.HomesharingDivider
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreen(
        modifier = Modifier.fillMaxSize(),
        email = state.email,
        password = state.password,
        onChangeEmail = viewModel::onEmailChange,
        onChangePassword = viewModel::onPasswordChangeChange,
        signIn = viewModel::singIn,
        getCode = viewModel::getCode
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    signIn: () -> Unit,
    getCode: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        val focusRequester = remember { FocusRequester() }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(HomesharingTheme.shape.roundedCornerShape62top)
                .background(HomesharingTheme.colors.white)
                .padding(vertical = 32.dp)
                .navigationBarsPadding()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(Res.drawable.lockbox_logo_horizontal),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .size(190.dp, 40.dp)
            )

            Text(
                text = stringResource(Res.string.check_in_online),
                style = HomesharingTheme.typography.bodyMedium,
                color = HomesharingTheme.colors.black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp)
            )

            HomesharingInput(
                value = email,
                onValueChange = onChangeEmail,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.None,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequester.requestFocus()
                    }
                ),
                trailingIcon = painterResource(Res.drawable.ic_close),
                trailingIconAction = {
                    onChangeEmail("")
                },
                placeholderText = stringResource(Res.string.placeholder_email),
                modifier = Modifier
                    .padding(top = 19.dp)
                    .padding(horizontal = 11.dp)
                    .fillMaxWidth()
            )

            var isSeen by remember { mutableStateOf(true) }
            HomesharingInput(
                value = password,
                onValueChange = onChangePassword,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.None,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { signIn() }
                ),
                trailingIcon = if (isSeen) {
                    painterResource(Res.drawable.ic_eye_off)
                } else {
                    painterResource(Res.drawable.ic_eye)
                },
                trailingIconAction = {
                    isSeen = !isSeen
                },
                visualTransformation = if (isSeen) PasswordVisualTransformation() else VisualTransformation.None,
                placeholderText = stringResource(Res.string.password_placeholder),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 11.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )

            HomesharingButton(
                title = stringResource(Res.string.sign_in),
                onClick = signIn,
                enabled = email.isNotBlank() && password.isNotBlank(),
                modifier = Modifier
                    .padding(top = 11.dp)
                    .padding(horizontal = 11.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 19.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                HomesharingDivider(
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = stringResource(Res.string.or),
                    style = HomesharingTheme.typography.bodyMedium,
                    color = HomesharingTheme.colors.black.copy(alpha = .3f),

                )

                HomesharingDivider(
                    modifier = Modifier.weight(1f)
                )
            }

            HomesharingButton(
                title = stringResource(Res.string.get_code),
                enabled = email.isNotBlank(),
                onClick = getCode,
                modifier = Modifier
                    .padding(top = 11.dp)
                    .padding(horizontal = 11.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    HomesharingTheme {
        LoginScreen()
    }
}