package org.homesharing.lockbox.presentation_v2.navigation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.homesharing.lockbox.presentation.uikit.animation.enterTransition
import org.homesharing.lockbox.presentation.uikit.animation.exitTransition
import org.homesharing.lockbox.presentation.uikit.animation.popExitTransition
import org.homesharing.lockbox.presentation_v2.login.LoginScreen

@Composable
internal fun LoginAuthNavigation(
    modifier: Modifier = Modifier,
) {
    val backstack = remember {
        mutableStateListOf<LoginRoutes>(LoginRoutes.Login)
    }
    NavDisplay(
        modifier = modifier,
        entryProvider = entryProvider {
            entry<LoginRoutes.Login> {
                LoginScreen()
            }
        },
        backStack = backstack,
        onBack = {
            backstack.removeLastOrNull()
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