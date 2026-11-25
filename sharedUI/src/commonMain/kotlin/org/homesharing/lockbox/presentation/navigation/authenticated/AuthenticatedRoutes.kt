package org.homesharing.lockbox.presentation.navigation.authenticated

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface AuthenticatedRoutes : NavKey {
    @Serializable
    data object NameSetup : AuthenticatedRoutes

    @Serializable
    data object PickRole : AuthenticatedRoutes

    @Serializable
    data object AddFlat : AuthenticatedRoutes

    @Serializable
    data object FlatAddress : AuthenticatedRoutes

    @Serializable
    data object FlatRooms : AuthenticatedRoutes

    @Serializable
    data object CheckFlatData : AuthenticatedRoutes

    @Serializable
    data object FlatCreated : AuthenticatedRoutes

    @Serializable
    data object GuestInfo : AuthenticatedRoutes

    @Serializable
    data object GuestInvitation : AuthenticatedRoutes

    @Serializable
    data object CheckInSetup : AuthenticatedRoutes

    @Serializable
    data object CheckInvitation : AuthenticatedRoutes

    @Serializable
    data object InvitationSent : AuthenticatedRoutes

    @Serializable
    data class FlatDetailed(val flatId: String) : AuthenticatedRoutes

    @Serializable
    data object FlatList : AuthenticatedRoutes
}