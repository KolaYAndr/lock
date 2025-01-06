package org.homesharing.lockbox.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.homesharing.lockbox.navigation.routes.AccountRoutes
import org.homesharing.lockbox.navigation.routes.MainRoutes
import org.homesharing.lockbox.navigation.routes.Routes
import org.homesharing.lockbox.navigation.routes.SettingsRoutes
import org.homesharing.lockbox.presentation.about.AboutScreen
import org.homesharing.lockbox.presentation.account.AccountScreen
import org.homesharing.lockbox.presentation.account.AccountViewModel
import org.homesharing.lockbox.presentation.login.LoginScreen
import org.homesharing.lockbox.presentation.login.LoginViewModel
import org.homesharing.lockbox.presentation.security.SecurityScreen
import org.homesharing.lockbox.presentation.security.SecurityViewModel
import org.homesharing.lockbox.presentation.settings.SettingsScreen
import org.homesharing.lockbox.presentation.settings.SettingsViewModel
import org.homesharing.lockbox.presentation.warning.WarningScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController, startDestination = Routes.Main, modifier = modifier
    ) {
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

        navigation<Routes.Settings>(
            startDestination = SettingsRoutes.Settings,
        ) {
            composable<SettingsRoutes.Settings> {
                val settingsViewModel = koinViewModel<SettingsViewModel>()
                val settingsState = settingsViewModel.uiState.collectAsStateWithLifecycle()
                SettingsScreen(
                    settingsUiState = settingsState.value,
                    onLogOut = {
                        settingsViewModel.logOut()
                        navController.navigate(MainRoutes.Login)
                    },
                    onHideLogOut = settingsViewModel::hideDialog,
                    onShowLogOut = settingsViewModel::showDialog,
                    onNavigate = { route -> navController.navigate(route) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<SettingsRoutes.AboutApp> {
                AboutScreen(
                    onNavBack = { navController.navigateUp() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<SettingsRoutes.Security> {
                val securityViewModel = koinViewModel<SecurityViewModel>()
                val securityUiState = securityViewModel.uiState.collectAsStateWithLifecycle()
                SecurityScreen(
                    securityUiState = securityUiState.value,
                    onNavBack = { navController.navigateUp() },
                    onShowDialog = { securityViewModel.showDialog() },
                    onHideDialog = { securityViewModel.hideDialog() },
                    onPasswordSet = { securityViewModel.onPasswordChanged() },
                    onReady = { navController.navigate(SettingsRoutes.Account) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            navigation<SettingsRoutes.Account>(
                startDestination = AccountRoutes.Account,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(durationMillis = 300)
                    )
                }
            ) {
                composable<AccountRoutes.Account> {
                    val accountViewModel = koinViewModel<AccountViewModel>()
                    val accountUiState = accountViewModel.uiState.collectAsStateWithLifecycle()
                    AccountScreen(
                        accountUiState = accountUiState.value,
                        onNavigateBack = { navController.navigateUp() },
                        onHideDialog = { accountViewModel.hideDialog() },
                        onShowDialog = { accountViewModel.showDialog() },
                        onDelete = {
                            accountViewModel.deleteAccount()
                            navController.navigate(AccountRoutes.Warning)
                        },
                        onNavigate = { route -> navController.navigate(route) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable<AccountRoutes.Warning> {
                    WarningScreen(
                        onNavigate = { navController.navigate(MainRoutes.Login) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable<AccountRoutes.ChangeEmail> {

                }

                composable<AccountRoutes.ChangePhoneNumber> {

                }
            }
        }
    }
}
