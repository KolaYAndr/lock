package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

internal sealed interface MainRoutes {
    @Serializable
    data object Boxes: MainRoutes

    @Serializable
    data class DetailedBox(val macAddress: String): MainRoutes

    @Serializable
    data object Login : MainRoutes
}
