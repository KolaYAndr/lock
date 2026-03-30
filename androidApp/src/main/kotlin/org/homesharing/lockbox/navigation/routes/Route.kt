package org.homesharing.lockbox.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface Route : NavKey {
    val showBottomBar: Boolean

    @Serializable
    data object Boxes : Route {
        override val showBottomBar = true
    }

    @Serializable
    data class DetailedBox(val macAddress: String) : Route {
        override val showBottomBar = false

    }

    @Serializable
    data object Contracts : Route {
        override val showBottomBar = true
    }

    @Serializable
    data object Settings : Route {
        override val showBottomBar = true
    }

    @Serializable
    data object Account : Route {
        override val showBottomBar: Boolean = false
    }

    @Serializable
    data object AddPhoneNumber : Route {
        override val showBottomBar: Boolean = false
    }

    @Serializable
    data object ChangeEmail : Route {
        override val showBottomBar: Boolean = false
    }

    @Serializable
    data object EmailChangedSuccessfully : Route {
        override val showBottomBar: Boolean = false
    }

    @Serializable
    data object Warning : Route {
        override val showBottomBar: Boolean = false
    }

    @Serializable
    data object Security : Route {
        override val showBottomBar: Boolean = false
    }

    @Serializable
    data object ActiveSessions : Route {
        override val showBottomBar: Boolean = false
    }

    @Serializable
    data object AboutApp : Route {
        override val showBottomBar: Boolean = false
    }
}