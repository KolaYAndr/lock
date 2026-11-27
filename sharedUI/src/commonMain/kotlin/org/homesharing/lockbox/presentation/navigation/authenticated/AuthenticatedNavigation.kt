package org.homesharing.lockbox.presentation.navigation.authenticated

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.homesharing.lockbox.presentation.LoadingContent
import org.homesharing.lockbox.presentation.add_flat_flow.add_or_import_flat.AddFlatScreen
import org.homesharing.lockbox.presentation.add_flat_flow.flat_address.FlatAddressScreen
import org.homesharing.lockbox.presentation.add_flat_flow.flat_created.FlatCreatedScreen
import org.homesharing.lockbox.presentation.add_flat_flow.flat_data.FlatDataScreen
import org.homesharing.lockbox.presentation.add_flat_flow.flat_rooms.FlatRoomsScreen
import org.homesharing.lockbox.presentation.flat_detailed.FlatDetailedScreen
import org.homesharing.lockbox.presentation.flat_list.FlatListScreen
import org.homesharing.lockbox.presentation.guest_invitation_flow.check_in_setup.CheckInSetupScreen
import org.homesharing.lockbox.presentation.guest_invitation_flow.check_invitation.CheckInvitationScreen
import org.homesharing.lockbox.presentation.guest_invitation_flow.guest_info.GuestInfoScreen
import org.homesharing.lockbox.presentation.guest_invitation_flow.guest_invited.GuestInvitedScreen
import org.homesharing.lockbox.presentation.guest_invitation_flow.invitation_details.GuestInvitationScreen
import org.homesharing.lockbox.presentation.setup_profile_flow.name_setup.NameSetupScreen
import org.homesharing.lockbox.presentation.setup_profile_flow.role_picker.RolePickerScreen
import org.homesharing.lockbox.presentation.uikit.animation.enterTransition
import org.homesharing.lockbox.presentation.uikit.animation.exitTransition
import org.homesharing.lockbox.presentation.uikit.animation.popExitTransition
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun HomesharingNavigation(
    modifier: Modifier = Modifier,
    navigator: AuthenticatedNavigationViewModel = koinViewModel(),
) {
    Crossfade(
        modifier = modifier,
        targetState = navigator.backstack.isEmpty(),
        label = "authenticated_loading",
    ) { isEmpty ->
        if (isEmpty) {
            LoadingContent(modifier = Modifier.fillMaxSize())
        } else {
            NavDisplay(
                modifier = Modifier.fillMaxSize(),
                entryProvider = entryProvider {
            entry<AuthenticatedRoutes.NameSetup> {
                NameSetupScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.PickRole)
                    }
                )
            }
            entry<AuthenticatedRoutes.PickRole> {
                RolePickerScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.AddFlat)
                    }
                )
            }
            entry<AuthenticatedRoutes.AddFlat> {
                AddFlatScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.FlatAddress)
                    }
                )
            }
            entry<AuthenticatedRoutes.FlatAddress> {
                FlatAddressScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.FlatRooms)
                    },
                    onNavBack = navigator::popBack,
                )
            }
            entry<AuthenticatedRoutes.FlatRooms> {
                FlatRoomsScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.CheckFlatData)
                    },
                    onNavBack = navigator::popBack,
                )
            }
            entry<AuthenticatedRoutes.CheckFlatData> {
                FlatDataScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.FlatCreated)
                    },
                    onNavBack = navigator::popBack,
                    onChangeAddress = {
                        navigator.popUpToOrAdd(AuthenticatedRoutes.FlatAddress)
                    },
                    onChangeFlatType = {
                        navigator.popUpToOrAdd(AuthenticatedRoutes.FlatAddress)
                    },
                    onChangeRooms = {
                        navigator.popUpToOrAdd(AuthenticatedRoutes.FlatRooms)
                    }
                )
            }
            entry<AuthenticatedRoutes.FlatCreated> {
                FlatCreatedScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.GuestInfo)
                    }
                )
            }
            entry<AuthenticatedRoutes.GuestInfo> {
                GuestInfoScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.GuestInvitation)
                    },
                    onNavBack = navigator::popBack,
                )
            }
            entry<AuthenticatedRoutes.GuestInvitation> {
                GuestInvitationScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.CheckInSetup)
                    },
                    onNavBack = navigator::popBack,
                )
            }
            entry<AuthenticatedRoutes.CheckInSetup> {
                CheckInSetupScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.CheckInvitation)
                    },
                    onNavBack = navigator::popBack,
                )
            }
            entry<AuthenticatedRoutes.CheckInvitation> {
                CheckInvitationScreen(
                    onProceed = {
                        navigator.navigateTo(AuthenticatedRoutes.InvitationSent)
                    },
                    onNavBack = navigator::popBack,
                    onChangeDates = {
                        navigator.popUpToOrAdd(AuthenticatedRoutes.GuestInfo)
                    },
                    onChangeSetup = {
                        navigator.popUpToOrAdd(AuthenticatedRoutes.CheckInSetup)
                    },
                    onChangeFlatInfo = {
                        navigator.popUpToOrAdd(AuthenticatedRoutes.FlatAddress)
                    },
                    onChangeGuestInfo = {
                        navigator.popUpToOrAdd(AuthenticatedRoutes.GuestInfo)
                    }
                )
            }
            entry<AuthenticatedRoutes.InvitationSent> {
                GuestInvitedScreen()
            }
            entry<AuthenticatedRoutes.FlatDetailed> { route ->
                FlatDetailedScreen(
                    viewModel = koinViewModel {
                        parametersOf(route.flatId)
                    },
                    onNavBack = navigator::popBack,
                )
            }
            entry<AuthenticatedRoutes.FlatList> {
                FlatListScreen(
                    onNavigateToDetails = { id ->
                        navigator.navigateTo(
                            AuthenticatedRoutes.FlatDetailed(id)
                        )
                    }
                )
            }
        },
        backStack = navigator.backstack,
        onBack = navigator::popBack,
        transitionSpec = enterTransition,
        popTransitionSpec = exitTransition,
        predictivePopTransitionSpec = popExitTransition,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                )
            )
        }
    }
}
