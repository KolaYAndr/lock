package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

sealed class ChangeEmailRoutes {
    @Serializable
    data object Main: ChangeEmailRoutes()

    @Serializable
    data object Successfully: ChangeEmailRoutes()
}