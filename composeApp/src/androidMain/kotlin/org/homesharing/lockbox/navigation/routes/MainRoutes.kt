package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

sealed class MainRoutes {
    @Serializable
    data object Main

    @Serializable
    data object Login
}