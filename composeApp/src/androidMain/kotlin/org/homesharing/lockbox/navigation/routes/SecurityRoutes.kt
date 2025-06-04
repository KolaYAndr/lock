package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

internal sealed class SecurityRoutes {
    @Serializable
    data object Security: SecurityRoutes()

    @Serializable
    data object ActiveSessions: SecurityRoutes()
}