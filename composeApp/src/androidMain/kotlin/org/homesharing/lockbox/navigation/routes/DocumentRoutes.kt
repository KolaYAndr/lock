package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

internal sealed interface DocumentRoutes {
    @Serializable
    data object Documents: DocumentRoutes

    @Serializable
    data object Login : DocumentRoutes
}