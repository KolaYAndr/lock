package org.homesharing.lockbox.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.presentation.navigation.authenticated.HomesharingNavigation
import org.homesharing.lockbox.presentation.navigation.login.LoginAuthNavigation
import org.homesharing.lockbox.presentation.uikit.animation.loginAnimation
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun LockboxApp() {
    LockboxApp(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
internal fun LockboxApp(
    modifier: Modifier = Modifier,
    viewModel: LockboxAppViewModel = koinViewModel(),
) {
    HomesharingTheme {
        val isLoggedIn by viewModel.loginState.collectAsStateWithLifecycle()

        Scaffold(
            modifier = modifier,
        ) { _ ->
            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = isLoggedIn,
                label = "authentication $isLoggedIn",
                transitionSpec = loginAnimation,
                contentKey = { it }
            ) { isLoggedIn ->
                if (isLoggedIn == LoginState.LoggedIn) {
                    HomesharingNavigation()
                } else {
                    LoginAuthNavigation()
                }
            }
        }
    }
}