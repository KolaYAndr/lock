package org.homesharing.lockbox.navigation.routes

import kotlinx.serialization.Serializable

internal sealed interface ContractRoutes {
    @Serializable
    data object Contracts: ContractRoutes

    @Serializable
    data object Login : ContractRoutes
}