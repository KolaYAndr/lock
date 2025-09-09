package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

internal sealed interface ChangeEmailRoutes {
    @Serializable
    data object Main: ChangeEmailRoutes

    @Serializable
    data object Successfully: ChangeEmailRoutes
}
