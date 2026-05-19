package org.homesharing.lockbox.presentation.navigation.login

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface LoginRoutes : NavKey {
    @Serializable
    data object Login : LoginRoutes
}