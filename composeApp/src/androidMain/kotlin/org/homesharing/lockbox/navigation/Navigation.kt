package org.homesharing.lockbox.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.homesharing.lockbox.navigation.routes.MainRoutes
import org.homesharing.lockbox.navigation.routes.Routes
import org.homesharing.lockbox.presentation.login.LoginScreen
import org.homesharing.lockbox.presentation.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Routes.Main, modifier = modifier) {
        navigation<Routes.Main>(startDestination = MainRoutes.Login) {
            composable<MainRoutes.Login> {
                val loginViewModel = koinViewModel<LoginViewModel>()
                val state = loginViewModel.uiState.collectAsStateWithLifecycle()
                LoginScreen(
                    state = state.value,
                    onDismiss = loginViewModel::hideCard,
                    onShowCard = loginViewModel::showCard,
                    onForgotPassword = loginViewModel::forgotPassword,
                    onSignIn = loginViewModel::signIn,
                    onSignUp = loginViewModel::signUp,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        composable<Routes.Menu> {
            Text("Settings")
        }
    }
}