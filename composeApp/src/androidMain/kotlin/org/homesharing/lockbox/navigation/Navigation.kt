package org.homesharing.lockbox.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.homesharing.lockbox.navigation.routes.Route
import org.homesharing.lockbox.presentation.about.AboutScreen
import org.homesharing.lockbox.presentation.account.AccountScreen
import org.homesharing.lockbox.presentation.add_phone_number.AddPhoneNumberScreen
import org.homesharing.lockbox.presentation.change_email.ChangeEmailScreen
import org.homesharing.lockbox.presentation.contracts.ContractsScreen
import org.homesharing.lockbox.presentation.detailed_lockbox.DetailedLockboxScreen
import org.homesharing.lockbox.presentation.lockboxes.LockboxesScreen
import org.homesharing.lockbox.presentation.security.SecurityScreen
import org.homesharing.lockbox.presentation.sessions.ActiveSessionScreen
import org.homesharing.lockbox.presentation.settings.SettingsScreen
import org.homesharing.lockbox.presentation.uikit.animation.enterTransition
import org.homesharing.lockbox.presentation.uikit.animation.exitTransition
import org.homesharing.lockbox.presentation.uikit.animation.popExitTransition
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.homesharing.lockbox.presentation.warning.WarningScreen

private const val EMAIL_CHANGE_DEEPLINK = "https://lockboxes.ru/redirect/email-change"

@Composable
internal fun AuthenticatedNavigation(
    navigator: Navigator,
    state: NavigationState,
    modifier: Modifier = Modifier,
    padding: PaddingValues,
) {
    NavDisplay(
        modifier = modifier
            .fillMaxSize()
            .background(LockBoxTheme.colors.grey950)
            .padding(padding),
        onBack = navigator::navBack,
        transitionSpec = enterTransition,
        popTransitionSpec = exitTransition,
        predictivePopTransitionSpec = popExitTransition,
        entries = state.toEntries(
            entryProvider {
                entry<Route.Boxes> {
                    LockboxesScreen(
                        navigateToDetails = { mac ->
                            navigator.navigate(Route.DetailedBox(mac))
                        },
                    )
                }

                entry<Route.DetailedBox> { key ->
                    DetailedLockboxScreen(
                        mac = key.macAddress,
                        onNavBack = navigator::navBack,
                    )
                }

                entry<Route.Contracts> {
                    ContractsScreen()
                }

                entry<Route.Settings> {
                    SettingsScreen(
                        onNavigate = navigator::navigate
                    )
                }
                
                entry<Route.Account> {
                    AccountScreen(
                        onNavBack = navigator::navBack
                    )
                }

                entry<Route.AddPhoneNumber> {
                    AddPhoneNumberScreen(
                        onNavBack = navigator::navBack
                    )
                }

                entry<Route.ChangeEmail> {
                    ChangeEmailScreen(
                        onNavBack = navigator::navBack,
                    )
                }

                entry<Route.Warning> {
                    WarningScreen()
                }

                entry<Route.Security> {
                    SecurityScreen(
                        onNavBack = navigator::navBack,
                        navigateToActiveSessions = {
                            navigator.navigate(Route.ActiveSessions)
                        }
                    )
                }

                entry<Route.ActiveSessions> {
                    ActiveSessionScreen(
                        onNavBack = navigator::navBack,
                    )
                }

                entry<Route.AboutApp> {
                    AboutScreen(
                        onNavBack = navigator::navBack,
                    )
                }
            }
        )
    )
}
