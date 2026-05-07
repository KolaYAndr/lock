package org.homesharing.lockbox.presentation_v2.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation_v2.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.androidx.compose.koinViewModel

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
        signIn = viewModel::singIn

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
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.background),
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
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .navigationBarsPadding()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.sign_in_account),
                style = HomesharingTheme.typography.titleLarge,
                color = HomesharingTheme.colors.black
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
                trailingIcon = painterResource(R.drawable.close),
                trailingIconAction = {
                    onChangeEmail("")
                },
                placeholderText = stringResource(R.string.placeholder_email),
                modifier = Modifier
                    .padding(top = 32.dp)
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
                trailingIcon = if (isSeen) painterResource(R.drawable.eye_off) else painterResource(R.drawable.eye),
                trailingIconAction = {
                    isSeen = !isSeen
                },
                visualTransformation = if (isSeen) PasswordVisualTransformation() else VisualTransformation.None,
                placeholderText = stringResource(R.string.password_placeholder),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )

            HomesharingButton(
                title = stringResource(R.string.sign_in),
                onClick = signIn,
                modifier = Modifier
                    .padding(top = 16.dp)
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