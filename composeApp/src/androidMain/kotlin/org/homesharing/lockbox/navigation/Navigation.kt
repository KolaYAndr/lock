package org.homesharing.lockbox.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import org.homesharing.lockbox.R
import org.homesharing.lockbox.navigation.routes.AccountRoutes
import org.homesharing.lockbox.navigation.routes.ChangeEmailRoutes
import org.homesharing.lockbox.navigation.routes.MainRoutes
import org.homesharing.lockbox.navigation.routes.Routes
import org.homesharing.lockbox.navigation.routes.SecurityRoutes
import org.homesharing.lockbox.navigation.routes.SettingsRoutes
import org.homesharing.lockbox.presentation.about.AboutScreen
import org.homesharing.lockbox.presentation.account.AccountScreen
import org.homesharing.lockbox.presentation.account.AccountViewModel
import org.homesharing.lockbox.presentation.add_phone_number.AddPhoneNumberScreen
import org.homesharing.lockbox.presentation.add_phone_number.AddPhoneNumberViewModel
import org.homesharing.lockbox.presentation.change_email.ChangeEmailScreen
import org.homesharing.lockbox.presentation.change_email.ChangeEmailViewModel
import org.homesharing.lockbox.presentation.change_email.EmailChangedSuccessfullyScreen
import org.homesharing.lockbox.presentation.lockboxes.LockBoxesScreen
import org.homesharing.lockbox.presentation.lockboxes.LockBoxesViewModel
import org.homesharing.lockbox.presentation.login.LoginScreen
import org.homesharing.lockbox.presentation.login.LoginViewModel
import org.homesharing.lockbox.presentation.security.SecurityScreen
import org.homesharing.lockbox.presentation.security.SecurityViewModel
import org.homesharing.lockbox.presentation.settings.SettingsScreen
import org.homesharing.lockbox.presentation.settings.SettingsViewModel
import org.homesharing.lockbox.presentation.warning.WarningScreen
import org.koin.androidx.compose.koinViewModel
import presentation.theme.LockBoxTheme

private const val ANIMATION_DURATION = 300
private val exitAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.End,
            animationSpec = tween(durationMillis = ANIMATION_DURATION)
        )
    }
private val enterAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            animationSpec = tween(durationMillis = ANIMATION_DURATION)
        )
    }

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
                    onToTypeAuth = loginViewModel::toTypeAuth,
                    onLoggedIn = { navController.navigate(MainRoutes.Boxes) },
                    topRowContent = {
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 12.dp)
                                .size(40.dp)
                                .background(
                                    color = LockBoxTheme.colors.grey800,
                                    shape = LockBoxTheme.shape.circleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.user),
                                tint = LockBoxTheme.colors.grey500,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(28.dp)

                            )
                        }
                        Text(
                            text = stringResource(R.string.main),
                            style = LockBoxTheme.typography.titleMedium,
                            color = LockBoxTheme.colors.grey50
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<MainRoutes.Boxes> {
                val lockBoxesViewModel = koinViewModel<LockBoxesViewModel>()
                val uiState = lockBoxesViewModel.uiState.collectAsStateWithLifecycle()

                LockBoxesScreen(
                    state = uiState.value,
                    boxes = lockBoxesViewModel.boxes,
                    onSearchChange = lockBoxesViewModel::onSearchChange,
                    onNavigateToBox = {},
                    onShowModal = lockBoxesViewModel::showModal,
                    onHideModal = lockBoxesViewModel::hideModal,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        navigation<Routes.Settings>(
            startDestination = SettingsRoutes.Login,
        ) {
            composable<SettingsRoutes.Login> {
                val loginViewModel = koinViewModel<LoginViewModel>()
                val state = loginViewModel.uiState.collectAsStateWithLifecycle()

                LoginScreen(
                    state = state.value,
                    onDismiss = loginViewModel::hideCard,
                    onShowCard = loginViewModel::showCard,
                    onToTypeAuth = loginViewModel::toTypeAuth,
                    onLoggedIn = { navController.navigate(SettingsRoutes.Settings) },
                    topRowContent = {
                        Text(
                            text = stringResource(R.string.settings),
                            style = LockBoxTheme.typography.titleSmall,
                            color = LockBoxTheme.colors.grey100,
                            modifier = Modifier.padding(12.dp)
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

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

            composable<SettingsRoutes.AboutApp>(
                enterTransition = enterAnimation,
                exitTransition = exitAnimation,
            ) {
                AboutScreen(
                    onNavBack = { navController.navigateUp() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            navigation<SettingsRoutes.Security>(
                enterTransition = enterAnimation,
                exitTransition = exitAnimation,
                startDestination = SecurityRoutes.Security
            ) {
                composable<SecurityRoutes.Security> {
                    val securityViewModel = koinViewModel<SecurityViewModel>()
                    val securityUiState = securityViewModel.uiState.collectAsStateWithLifecycle()

                    SecurityScreen(
                        securityUiState = securityUiState.value,
                        onNavBack = { navController.navigateUp() },
                        onToActiveSessions = { navController.navigate(SecurityRoutes.ActiveSessions) },
                        onShowDialog = { securityViewModel.showDialog() },
                        onHideDialog = { securityViewModel.hideDialog() },
                        onPasswordSet = { securityViewModel.onPasswordChanged() },
                        onReady = { navController.navigate(SettingsRoutes.Account) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable<SecurityRoutes.ActiveSessions> {

                }
            }

            navigation<SettingsRoutes.Account>(
                startDestination = AccountRoutes.Account,
                enterTransition = enterAnimation,
                exitTransition = exitAnimation,
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
                        onChangeName = accountViewModel::onNameChange,
                        onSurnameChanged = accountViewModel::onSurnameChanged,
                        onNameChanged = accountViewModel::onNameChanged,
                        onChangeSurname = accountViewModel::onSurnameChange,
                        onSendImage = accountViewModel::sendImage,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable<AccountRoutes.Warning> {
                    WarningScreen(
                        onNavigate = { navController.navigate(MainRoutes.Login) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                navigation<AccountRoutes.ChangeEmail>(
                    startDestination = ChangeEmailRoutes.Main
                ) {
                    composable<ChangeEmailRoutes.Main> {
                        val changeEmailViewModel = koinViewModel<ChangeEmailViewModel>()
                        val changeEmailUiState =
                            changeEmailViewModel.uiState.collectAsStateWithLifecycle()

                        ChangeEmailScreen(
                            uiState = changeEmailUiState.value,
                            onNavBack = { navController.navigateUp() },
                            onToStep = changeEmailViewModel::toStep,
                            onEmailChange = changeEmailViewModel::onEmailChange,
                            changeEmail = changeEmailViewModel::changeEmail,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    composable<ChangeEmailRoutes.Successfully>(
                        deepLinks = listOf(
                            navDeepLink<ChangeEmailRoutes.Successfully>(basePath = "https://api.lockboxes.ru/successfully") //TODO изменить при согласовании диплинка
                        )
                    ) {
                        EmailChangedSuccessfullyScreen(
                            modifier = Modifier.fillMaxSize(),
                            onDone = { navController.navigate(SettingsRoutes.Account) }
                        )
                    }
                }

                composable<AccountRoutes.ChangePhoneNumber> {
                    val addPhoneNumberViewModel = koinViewModel<AddPhoneNumberViewModel>()
                    val addPhoneUiState =
                        addPhoneNumberViewModel.uiState.collectAsStateWithLifecycle()

                    AddPhoneNumberScreen(
                        uiState = addPhoneUiState.value,
                        onNavBack = { navController.navigateUp() },
                        onToStep = addPhoneNumberViewModel::toStep,
                        onNumberChange = addPhoneNumberViewModel::onNumberChange,
                        onSendCode = addPhoneNumberViewModel::sendConfirmationCode,
                        onDone = { navController.navigate(SettingsRoutes.Account) },
                        onConfirmationCodeChanged = addPhoneNumberViewModel::onConfirmationCodeChanged,
                        onConfirm = addPhoneNumberViewModel::confirmCode,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
