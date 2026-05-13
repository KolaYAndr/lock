package org.homesharing.lockbox.presentation_v2.navigation.authenticated

import androidx.navigation3.runtime.NavKey

internal sealed interface AuthenticatedRoutes: NavKey {
    data object ProfileSetup: AuthenticatedRoutes
    data object ChooseRole: AuthenticatedRoutes

    data object AddFlat: AuthenticatedRoutes
    data object FlatAddress: AuthenticatedRoutes
    data object FlatRooms: AuthenticatedRoutes
    data object CheckFlatData: AuthenticatedRoutes
    data object FlatCreated: AuthenticatedRoutes

    data object GuestInfo: AuthenticatedRoutes
    data object GuestInvitation: AuthenticatedRoutes
    data object CheckInSetup: AuthenticatedRoutes
    data object CheckInvitation: AuthenticatedRoutes
    data object InvitationSent: AuthenticatedRoutes
}