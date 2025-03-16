package org.homesharing.lockbox.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
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
import org.homesharing.lockbox.presentation.detailed_lockbox.DetailedLockBoxScreen
import org.homesharing.lockbox.presentation.detailed_lockbox.DetailedLockBoxViewModel
import org.homesharing.lockbox.presentation.lockboxes.LockBoxesScreen
import org.homesharing.lockbox.presentation.lockboxes.LockBoxesViewModel
import org.homesharing.lockbox.presentation.login.LoginScreen
import org.homesharing.lockbox.presentation.login.LoginViewModel
import org.homesharing.lockbox.presentation.security.SecurityScreen
import org.homesharing.lockbox.presentation.security.SecurityViewModel
import org.homesharing.lockbox.presentation.sessions.ActiveSessionScreen
import org.homesharing.lockbox.presentation.sessions.ActiveSessionsViewModel
import org.homesharing.lockbox.presentation.settings.SettingsScreen
import org.homesharing.lockbox.presentation.settings.SettingsViewModel
import org.homesharing.lockbox.presentation.warning.WarningScreen
import org.koin.androidx.compose.koinViewModel
import presentation.composables.LockBoxesTopRow
import presentation.composables.SettingsTopRowText

private const val EXIT_ANIMATION_DURATION = 300
private const val ENTER_ANIMATION_DURATION = 200
private val exitAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    {
        fadeOut(
            animationSpec = tween(
                EXIT_ANIMATION_DURATION, easing = LinearEasing
            )
        ) + slideOutOfContainer(
            animationSpec = tween(EXIT_ANIMATION_DURATION, easing = LinearEasing),
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            targetOffset = { -it / 4 }
        )
    }
private val enterAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    {
        fadeIn(
            animationSpec = tween(
                ENTER_ANIMATION_DURATION, easing = LinearEasing
            )
        ) + slideIntoContainer(
            animationSpec = tween(ENTER_ANIMATION_DURATION, easing = LinearEasing),
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            initialOffset = { it }
        )
    }

@Composable
fun Navigation(
    navController: NavHostController,
    startFromLogin: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Main,
        modifier = modifier,
        exitTransition = { ExitTransition.None },
        enterTransition = { EnterTransition.None }
    ) {
        navigation<Routes.Main>(startDestination = if (startFromLogin) MainRoutes.Login else MainRoutes.Boxes) {
            composable<MainRoutes.Login> {
                val loginViewModel = koinViewModel<LoginViewModel>()
                val state = loginViewModel.uiState.collectAsStateWithLifecycle()

                LoginScreen(
                    state = state.value,
                    onToTypeAuth = loginViewModel::toTypeAuth,
                    onLoggedIn = {
                        navController.navigateToDestinationPoppingBackStack(MainRoutes.Boxes)
                    },
                    topRowContent = { LockBoxesTopRow() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<MainRoutes.Boxes> {
                val lockBoxesViewModel = koinViewModel<LockBoxesViewModel>()
                val uiState = lockBoxesViewModel.uiState.collectAsStateWithLifecycle()

                LockBoxesScreen(
                    state = uiState.value,
                    boxes = lockBoxesViewModel.boxes,
                    onNavigateToBox = { macAddress ->
                        navController.navigate(
                            MainRoutes.DetailedBox(macAddress = macAddress)
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<MainRoutes.DetailedBox>(
                enterTransition = enterAnimation,
                exitTransition = exitAnimation,
            ) { backStackEntry: NavBackStackEntry ->
                val args = backStackEntry.toRoute<MainRoutes.DetailedBox>()
                val macAddress = args.macAddress

                val detailedLockBoxViewModel = koinViewModel<DetailedLockBoxViewModel>()
                LaunchedEffect(macAddress) {
                    detailedLockBoxViewModel.findBoxByMac(macAddress)
                }
                val state = detailedLockBoxViewModel.uiState.collectAsStateWithLifecycle()

                DetailedLockBoxScreen(
                    uiState = state.value,
                    onNavBack = navController::navigateUp,
                    onOpenBox = detailedLockBoxViewModel::openBox,
                    onUnbindDevice = {
                        detailedLockBoxViewModel.unbindDevice(
                            macAddress = macAddress,
                            onSuccess = navController::navigateUp
                        )
                    },
                    onSaveAddress = detailedLockBoxViewModel::saveAddress,
                    onSaveName = detailedLockBoxViewModel::saveName,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        navigation<Routes.Settings>(
            startDestination = if (startFromLogin) SettingsRoutes.Login else SettingsRoutes.Settings,
        ) {
            composable<SettingsRoutes.Login> {
                val loginViewModel = koinViewModel<LoginViewModel>()
                val state = loginViewModel.uiState.collectAsStateWithLifecycle()

                LoginScreen(
                    state = state.value,
                    onToTypeAuth = loginViewModel::toTypeAuth,
                    onLoggedIn = {
                        navController.navigateToDestinationPoppingBackStack(SettingsRoutes.Settings)
                    },
                    topRowContent = { SettingsTopRowText() },
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
                        navController.navigateToDestinationPoppingBackStack(MainRoutes.Login)
                    },
                    onRefresh = settingsViewModel::refreshAccountInfo,
                    onNavigate = { route -> navController.navigate(route) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<SettingsRoutes.AboutApp>(
                enterTransition = enterAnimation,
                exitTransition = exitAnimation,
            ) {
                AboutScreen(
                    onNavBack = navController::navigateUp,
                    modifier = Modifier.fillMaxSize()
                )
            }

            navigation<SettingsRoutes.Security>(
                startDestination = SecurityRoutes.Security
            ) {
                composable<SecurityRoutes.Security>(
                    enterTransition = enterAnimation,
                    exitTransition = exitAnimation,
                ) {
                    val securityViewModel = koinViewModel<SecurityViewModel>()
                    val securityUiState = securityViewModel.uiState.collectAsStateWithLifecycle()

                    SecurityScreen(
                        securityUiState = securityUiState.value,
                        onNavBack = navController::navigateUp,
                        onToActiveSessions = { navController.navigate(SecurityRoutes.ActiveSessions) },
                        onPasswordSet = securityViewModel::onPasswordChanged,
                        onReady = { navController.navigate(SettingsRoutes.Account) },
                        onBackToChangePassword = securityViewModel::backToChangePassword,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable<SecurityRoutes.ActiveSessions> {
                    val activeSessionsViewModel = koinViewModel<ActiveSessionsViewModel>()
                    val uiState = activeSessionsViewModel.uiState.collectAsStateWithLifecycle()

                    ActiveSessionScreen(
                        activeSessionsUiState = uiState.value,
                        onNavBack = navController::navigateUp,
                        onFinishSessions = activeSessionsViewModel::finishOtherSessions,
                        onRefresh = activeSessionsViewModel::refreshSessions,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            navigation<SettingsRoutes.Account>(
                startDestination = AccountRoutes.Account,
            ) {
                composable<AccountRoutes.Account>(
                    enterTransition = enterAnimation,
                    exitTransition = exitAnimation,
                ) {
                    val accountViewModel = koinViewModel<AccountViewModel>()
                    val accountUiState = accountViewModel.uiState.collectAsStateWithLifecycle()

                    AccountScreen(
                        accountUiState = accountUiState.value,
                        onNavigateBack = navController::navigateUp,
                        onDelete = {
                            accountViewModel.deleteAccount()
                            navController.navigateToDestinationPoppingBackStack(AccountRoutes.Warning)
                        },
                        onNavigate = { route -> navController.navigate(route) },
                        onChangeName = accountViewModel::onNameChange,
                        onChangeSurname = accountViewModel::onSurnameChange,
                        onSendImage = accountViewModel::sendImage,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable<AccountRoutes.Warning> {
                    WarningScreen(
                        onNavigate = {
                            navController.navigateToDestinationPoppingBackStack(MainRoutes.Login)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                navigation<AccountRoutes.ChangeEmail>(
                    startDestination = ChangeEmailRoutes.Main,
                ) {
                    composable<ChangeEmailRoutes.Main> {
                        val changeEmailViewModel = koinViewModel<ChangeEmailViewModel>()
                        val changeEmailUiState =
                            changeEmailViewModel.uiState.collectAsStateWithLifecycle()

                        ChangeEmailScreen(
                            uiState = changeEmailUiState.value,
                            onNavBack = navController::navigateUp,
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
                        onNavBack = navController::navigateUp,
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

private fun <T : Any> NavHostController.navigateToDestinationPoppingBackStack(destination: T) {
    this.popBackStack()
    this.navigate(destination) {
        launchSingleTop = true
        restoreState = false
    }
}
