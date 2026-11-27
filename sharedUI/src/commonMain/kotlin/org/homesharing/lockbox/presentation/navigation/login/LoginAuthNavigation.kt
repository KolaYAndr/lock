package org.homesharing.lockbox.presentation.navigation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.homesharing.lockbox.presentation.login.LoginScreen
import org.homesharing.lockbox.presentation.login_otp.LoginOtpScreen
import org.homesharing.lockbox.presentation.navigation.navBackstackSaver
import org.homesharing.lockbox.presentation.uikit.animation.enterTransition
import org.homesharing.lockbox.presentation.uikit.animation.exitTransition
import org.homesharing.lockbox.presentation.uikit.animation.popExitTransition
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun LoginAuthNavigation(
    modifier: Modifier = Modifier,
) {
    val backstack = rememberSaveable(
        saver = navBackstackSaver(LoginRoutes.serializer())
    ) {
        mutableStateListOf(LoginRoutes.Login)
    }
    NavDisplay(
        modifier = modifier,
        entryProvider = entryProvider {
            entry<LoginRoutes.Login> {
                LoginScreen(
                    onNavigateToLoginOtp = { email, resendAfter, expiresIn ->
                        backstack.add(
                            LoginRoutes.LoginOtp(
                                email = email,
                                resendAfter = resendAfter,
                                expiresIn = expiresIn,
                            )
                        )
                    }
                )
            }
            entry<LoginRoutes.LoginOtp> { route ->
                LoginOtpScreen(
                    viewModel = koinViewModel {
                        parametersOf(route.email, route.resendAfter)
                    },
                    onNewUser = {},
                    onExistingUser = {},
                    onNavBack = {
                        backstack.removeLastOrNull()
                    },
                )
            }
        },
        backStack = backstack,
        onBack = {
            if (backstack.size > 1) {
                backstack.removeLastOrNull()
            }
        },
        transitionSpec = enterTransition,
        popTransitionSpec = exitTransition,
        predictivePopTransitionSpec = popExitTransition,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        )
    )
}