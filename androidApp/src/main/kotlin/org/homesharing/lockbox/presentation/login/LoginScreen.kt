package org.homesharing.lockbox.presentation.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.sign_in.SignInCard
import org.homesharing.lockbox.presentation.sign_up.SignUpCard
import org.homesharing.lockbox.presentation.uikit.animation.backwardAnimation
import org.homesharing.lockbox.presentation.uikit.animation.forwardAnimation
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxBottomSheet
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
) {
    val loginType by loginViewModel.loginType.collectAsStateWithLifecycle()
    LoginScreen(
        loginType = loginType,
        toTypeAuth = loginViewModel::toTypeAuth,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun LoginScreen(
    loginType: LoginType,
    toTypeAuth: (LoginType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showModal by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .background(LockBoxTheme.colors.grey950)
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.log_in),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey500,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = stringResource(R.string.log_in_your_profile),
            style = LockBoxTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp),
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.start_using_today),
            style = LockBoxTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp),
            color = LockBoxTheme.colors.grey500,
            textAlign = TextAlign.Center
        )
        LockboxButton(
            onClick = { showModal = true },
            text = stringResource(R.string.sign_in),
            paddingValues = PaddingValues(10.dp),
            modifier = Modifier.padding(top = 16.dp)
        )
    }

    if (showModal) {
        LockboxBottomSheet(
            onDismissRequest = { showModal = false },
        ) {
            AnimatedContent(
                targetState = loginType,
                transitionSpec = {
                    if (targetState.hierarchyIndex > initialState.hierarchyIndex) {
                        forwardAnimation
                    } else {
                        backwardAnimation
                    }
                }
            ) { targetState ->
                when (targetState) {
                    LoginType.SignIn -> {
                        SignInCard(
                            onDismiss = { showModal = false },
                            toSignUp = { toTypeAuth(LoginType.SignUp) }
                        )
                    }

                    LoginType.SignUp -> {
                        SignUpCard(
                            onSignIn = { toTypeAuth(LoginType.SignIn) },
                            onDismiss = { showModal = false }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LockBoxTheme {
        LoginScreen(
            loginType = LoginType.SignIn,
            toTypeAuth = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
