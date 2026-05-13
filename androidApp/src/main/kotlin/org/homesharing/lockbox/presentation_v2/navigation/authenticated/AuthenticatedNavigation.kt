package org.homesharing.lockbox.presentation_v2.navigation.authenticated

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
import org.homesharing.lockbox.presentation_v2.flats_flow.add_or_import_flat.AddFlatScreen
import org.homesharing.lockbox.presentation_v2.flats_flow.flat_address.FlatAddressScreen
import org.homesharing.lockbox.presentation_v2.flats_flow.flat_created.FlatCreatedScreen
import org.homesharing.lockbox.presentation_v2.flats_flow.flat_data.FlatDataScreen
import org.homesharing.lockbox.presentation_v2.flats_flow.flat_rooms.FlatRoomsScreen
import org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.check_in_setup.CheckInSetupScreen
import org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.check_invitation.CheckInvitationScreen
import org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.guest_info.GuestInfoScreen
import org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.guest_invited.GuestInvitedScreen
import org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.invitation_details.GuestInvitationScreen
import org.homesharing.lockbox.presentation_v2.profile_flow.profile_setup.ProfileSetupScreen
import org.homesharing.lockbox.presentation_v2.profile_flow.role_chooser.RoleChooserScreen

@Composable
internal fun HomesharingNavigation(
    modifier: Modifier = Modifier,
) {
    val backstack = remember {
        mutableStateListOf<AuthenticatedRoutes>(AuthenticatedRoutes.ProfileSetup)
    }
    NavDisplay(
        modifier = modifier,
        entryProvider = entryProvider {
            entry<AuthenticatedRoutes.ProfileSetup> {
                ProfileSetupScreen()
            }
            entry<AuthenticatedRoutes.ChooseRole> {
                RoleChooserScreen()
            }
            entry<AuthenticatedRoutes.AddFlat> {
                AddFlatScreen()
            }
            entry<AuthenticatedRoutes.FlatAddress> {
                FlatAddressScreen()
            }
            entry<AuthenticatedRoutes.FlatRooms> {
                FlatRoomsScreen()
            }
            entry<AuthenticatedRoutes.CheckFlatData> {
                FlatDataScreen()
            }
            entry<AuthenticatedRoutes.FlatCreated> {
                FlatCreatedScreen()
            }
            entry<AuthenticatedRoutes.GuestInfo> {
                GuestInfoScreen()
            }
            entry<AuthenticatedRoutes.GuestInvitation> {
                GuestInvitationScreen()
            }
            entry<AuthenticatedRoutes.CheckInSetup> {
                CheckInSetupScreen()
            }
            entry<AuthenticatedRoutes.CheckInvitation> {
                CheckInvitationScreen()
            }
            entry<AuthenticatedRoutes.InvitationSent> {
                GuestInvitedScreen()
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