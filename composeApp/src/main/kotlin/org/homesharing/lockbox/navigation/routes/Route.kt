package org.homesharing.lockbox.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

internal sealed interface Route : NavKey {

    @Serializable
    data object Boxes : Route

    @Serializable
    data class DetailedBox(val macAddress: String) : Route

    @Serializable
    data object Contracts : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data object Account : Route

    @Serializable
    data object AddPhoneNumber : Route

    @Serializable
    data object ChangeEmail : Route

    @Serializable
    data object EmailChangedSuccessfully : Route

    @Serializable
    data object Warning : Route

    @Serializable
    data object Security : Route

    @Serializable
    data object ActiveSessions : Route

    @Serializable
    data object AboutApp : Route
}