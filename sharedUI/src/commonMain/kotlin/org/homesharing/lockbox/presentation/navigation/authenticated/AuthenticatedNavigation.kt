package org.homesharing.lockbox.presentation.navigation.authenticated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.homesharing.lockbox.presentation.flat_detailed.FlatDetailedScreen
import org.homesharing.lockbox.presentation.navigation.navBackstackSaver
import org.homesharing.lockbox.presentation.uikit.animation.enterTransition
import org.homesharing.lockbox.presentation.uikit.animation.exitTransition
import org.homesharing.lockbox.presentation.uikit.animation.popExitTransition
import org.homesharing.lockbox.presentation.flats_flow.add_or_import_flat.AddFlatScreen
import org.homesharing.lockbox.presentation.flats_flow.flat_address.FlatAddressScreen
import org.homesharing.lockbox.presentation.flats_flow.flat_created.FlatCreatedScreen
import org.homesharing.lockbox.presentation.flats_flow.flat_data.FlatDataScreen
import org.homesharing.lockbox.presentation.flats_flow.flat_rooms.FlatRoomsScreen
import org.homesharing.lockbox.presentation.guest_invitaion_flow.check_in_setup.CheckInSetupScreen
import org.homesharing.lockbox.presentation.guest_invitaion_flow.check_invitation.CheckInvitationScreen
import org.homesharing.lockbox.presentation.guest_invitaion_flow.guest_info.GuestInfoScreen
import org.homesharing.lockbox.presentation.guest_invitaion_flow.guest_invited.GuestInvitedScreen
import org.homesharing.lockbox.presentation.guest_invitaion_flow.invitation_details.GuestInvitationScreen
import org.homesharing.lockbox.presentation.profile_flow.profile_setup.ProfileSetupScreen
import org.homesharing.lockbox.presentation.profile_flow.role_chooser.RoleChooserScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun HomesharingNavigation(
    modifier: Modifier = Modifier,
) {
    val backstack = rememberSaveable(
        saver = navBackstackSaver(AuthenticatedRoutes.serializer())
    ) {
        mutableStateListOf(AuthenticatedRoutes.FlatDetailed("1234"))
    }
    NavDisplay(
        modifier = modifier,
        entryProvider = entryProvider {
            entry<AuthenticatedRoutes.ProfileSetup> {
                ProfileSetupScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.ChooseRole)
                    }
                )
            }
            entry<AuthenticatedRoutes.ChooseRole> {
                RoleChooserScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.AddFlat)
                    }
                )
            }
            entry<AuthenticatedRoutes.AddFlat> {
                AddFlatScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.FlatAddress)
                    }
                )
            }
            entry<AuthenticatedRoutes.FlatAddress> {
                FlatAddressScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.FlatRooms)
                    },
                    onNavBack = {
                        backstack.removeLastOrNull()
                    }
                )
            }
            entry<AuthenticatedRoutes.FlatRooms> {
                FlatRoomsScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.CheckFlatData)
                    },
                    onNavBack = {
                        backstack.removeLastOrNull()
                    }
                )
            }
            entry<AuthenticatedRoutes.CheckFlatData> {
                FlatDataScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.FlatCreated)
                    },
                    onNavBack = {
                        backstack.removeLastOrNull()
                    },
                    onChangeAddress = {
                        backstack.add(AuthenticatedRoutes.FlatAddress)
                    },
                    onChangeFlatType = {
                        backstack.add(AuthenticatedRoutes.FlatAddress)
                    },
                    onChangeRooms = {
                        backstack.add(AuthenticatedRoutes.FlatRooms)
                    }
                )
            }
            entry<AuthenticatedRoutes.FlatCreated> {
                FlatCreatedScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.GuestInfo)
                    }
                )
            }
            entry<AuthenticatedRoutes.GuestInfo> {
                GuestInfoScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.GuestInvitation)
                    },
                    onNavBack = {
                        backstack.removeLastOrNull()
                    }
                )
            }
            entry<AuthenticatedRoutes.GuestInvitation> {
                GuestInvitationScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.CheckInSetup)
                    },
                    onNavBack = {
                        backstack.removeLastOrNull()
                    }
                )
            }
            entry<AuthenticatedRoutes.CheckInSetup> {
                CheckInSetupScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.CheckInvitation)
                    },
                    onNavBack = {
                        backstack.removeLastOrNull()
                    }
                )
            }
            entry<AuthenticatedRoutes.CheckInvitation> {
                CheckInvitationScreen(
                    onProceed = {
                        backstack.add(AuthenticatedRoutes.InvitationSent)
                    },
                    onNavBack = {
                        backstack.removeLastOrNull()
                    },
                    onChangeDates = {
                        backstack.add(AuthenticatedRoutes.GuestInfo)
                    },
                    onChangeSetup = {
                        backstack.add(AuthenticatedRoutes.CheckInSetup)
                    },
                    onChangeFlatInfo = {
                        backstack.add(AuthenticatedRoutes.FlatAddress)
                    },
                    onChangeGuestInfo = {
                        backstack.add(AuthenticatedRoutes.GuestInfo)
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
                    onNavBack = {
                        backstack.removeLastOrNull()
                    }
                )
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