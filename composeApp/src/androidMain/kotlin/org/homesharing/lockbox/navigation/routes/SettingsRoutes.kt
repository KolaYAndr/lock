package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

sealed class SettingsRoutes {
    @Serializable
    data object Settings: SettingsRoutes()

    @Serializable
    data object Account: SettingsRoutes()

    @Serializable
    data object Security: SettingsRoutes()

    @Serializable
    data object AboutApp: SettingsRoutes()

    @Serializable
    data object Login: SettingsRoutes()
}