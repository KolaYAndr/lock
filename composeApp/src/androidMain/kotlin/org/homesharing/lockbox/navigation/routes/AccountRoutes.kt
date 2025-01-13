package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

sealed class AccountRoutes {
    @Serializable
    data object Account: AccountRoutes()

    @Serializable
    data object ChangePhoneNumber: AccountRoutes()

    @Serializable
    data object ChangeEmail: AccountRoutes()

    @Serializable
    data object Warning: AccountRoutes()
}
