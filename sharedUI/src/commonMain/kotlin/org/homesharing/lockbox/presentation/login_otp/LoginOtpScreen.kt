package org.homesharing.lockbox.presentation.login_otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.check_spam_folder
import lockbox.sharedui.generated.resources.check_your_email
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.otp_sent_to
import lockbox.sharedui.generated.resources.proceed
import lockbox.sharedui.generated.resources.send_code_again
import org.homesharing.lockbox.presentation.ActionOnSharedFlow
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingPinInput
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private const val OTP_LENGTH = 6

@Composable
internal fun LoginOtpScreen(
    viewModel: LoginOtpViewModel = koinViewModel(),
    onNewUser: () -> Unit = {},
    onExistingUser: () -> Unit = {},
    onNavBack: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ActionOnSharedFlow(
        lifecycleOwner = LocalLifecycleOwner.current,
        flow = viewModel.events,
        onFlowCollected = { event ->
            when (event) {
                LoginOtpEvent.NewUser -> onNewUser()
                LoginOtpEvent.ExistingUser -> onExistingUser()
            }
        },
    )

    LoginOtpScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onNavBack = onNavBack,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun LoginOtpScreen(
    state: LoginOtpUiState,
    onIntent: (LoginOtpIntent) -> Unit,
    onNavBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(HomesharingTheme.colors.white)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .imePadding(),
        verticalArrangement = Arrangement.Top,
    ) {
        CenterAlignedTopAppBar(
            title = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            navigationIcon = {
                IconButton(
                    onClick = onNavBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = HomesharingTheme.colors.black.copy(alpha = .5f)
                    )
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_chevron_left),
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(Res.string.check_your_email),
            style = HomesharingTheme.typography.titleLarge,
            color = HomesharingTheme.colors.black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )

        Text(
            text = stringResource(Res.string.otp_sent_to, state.email),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )

        Text(
            text = stringResource(Res.string.check_spam_folder),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )

        HomesharingPinInput(
            value = state.code,
            onValueChange = { onIntent(LoginOtpIntent.CodeChanged(it)) },
            isError = state.isError,
            keyboardActions = KeyboardActions(
                onDone = { onIntent(LoginOtpIntent.Submit) }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        )

        HomesharingButton(
            title = stringResource(Res.string.proceed),
            onClick = { onIntent(LoginOtpIntent.Submit) },
            enabled = state.code.length == OTP_LENGTH && !state.isLoading,
            loading = state.isLoading,
            style = HomesharingButtonDefaults.primary(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        )

        HomesharingButton(
            title = stringResource(Res.string.send_code_again),
            onClick = { onIntent(LoginOtpIntent.ResendCode) },
            enabled = state.isResendAvailable && !state.isLoading,
            style = HomesharingButtonDefaults.ghost(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewLoginOtpScreen() {
    HomesharingTheme {
        LoginOtpScreen(
            state = LoginOtpUiState.getInitial(
                email = "you@example.com",
            ),
            onIntent = {},
            onNavBack = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
