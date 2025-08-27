package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

internal sealed interface AccountRoutes {
    @Serializable
    data object Account: AccountRoutes

    @Serializable
    data object ChangePhoneNumber: AccountRoutes

    @Serializable
    data object ChangeEmail: AccountRoutes

    @Serializable
    data object Warning: AccountRoutes
}
