package org.homesharing.lockbox.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.presentation.navigation.authenticated.HomesharingNavigation
import org.homesharing.lockbox.presentation.navigation.login.LoginAuthNavigation
import org.homesharing.lockbox.presentation.uikit.animation.loginAnimation
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LockboxApp(
    modifier: Modifier = Modifier,
    viewModel: LockboxAppViewModel = koinViewModel(),
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    HomesharingTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
        ) { _ ->
            Crossfade(
                modifier = Modifier.fillMaxSize(),
                targetState = loginState == LoginState.Loading,
                label = "splash",
            ) { showSplash ->
                if (showSplash) {
                    SplashContent()
                } else {
                    val isLoggedIn = loginState == LoginState.LoggedIn
                    AnimatedContent(
                        modifier = Modifier.fillMaxSize(),
                        targetState = isLoggedIn,
                        label = "authentication $isLoggedIn",
                        transitionSpec = loginAnimation,
                        contentKey = { it },
                    ) { logged ->
                        if (logged) {
                            HomesharingNavigation()
                        } else {
                            LoginAuthNavigation()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LockboxApp(
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier,
) {
    HomesharingTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
        ) { _ ->
            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = isLoggedIn,
                label = "authentication $isLoggedIn",
                transitionSpec = loginAnimation,
                contentKey = { it },
            ) { logged ->
                if (logged) {
                    HomesharingNavigation()
                } else {
                    LoginAuthNavigation()
                }
            }
        }
    }
}