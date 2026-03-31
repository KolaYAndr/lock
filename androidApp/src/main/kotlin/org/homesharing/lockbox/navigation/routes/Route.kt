package org.homesharing.lockbox.navigation.routes

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.presentation.about.AboutAppTopBar
import org.homesharing.lockbox.presentation.account.AccountTopBar
import org.homesharing.lockbox.presentation.add_phone_number.AddPhoneNumberTopBar
import org.homesharing.lockbox.presentation.change_email.ChangeEmailTopBar
import org.homesharing.lockbox.presentation.detailed_lockbox.DetailLockboxTopBar
import org.homesharing.lockbox.presentation.security.SecurityTopBar
import org.homesharing.lockbox.presentation.sessions.ActiveSessionsTopBar

@Serializable
internal sealed interface Route : NavKey {
    val showBottomBar: Boolean
    val topBar: (@Composable (() -> Unit) -> Unit)?

    @Serializable
    data object Boxes : Route {
        override val showBottomBar = true
        override val topBar = null
    }

    @Serializable
    data class DetailedBox(val macAddress: String) : Route {
        override val showBottomBar = false
        override val topBar: @Composable (() -> Unit) -> Unit = { DetailLockboxTopBar(it) }
    }

    @Serializable
    data object Contracts : Route {
        override val showBottomBar = true
        override val topBar = null
    }

    @Serializable
    data object Settings : Route {
        override val showBottomBar = true
        override val topBar = null
    }

    @Serializable
    data object Account : Route {
        override val showBottomBar: Boolean = false
        override val topBar: @Composable (() -> Unit) -> Unit = { AccountTopBar(it) }
    }

    @Serializable
    data object AddPhoneNumber : Route {
        override val showBottomBar: Boolean = false
        override val topBar: @Composable (() -> Unit) -> Unit = { AddPhoneNumberTopBar(it) }
    }

    @Serializable
    data object ChangeEmail : Route {
        override val showBottomBar: Boolean = false
        override val topBar: @Composable (() -> Unit) -> Unit = { ChangeEmailTopBar(it) }
    }

    @Serializable
    data object EmailChangedSuccessfully : Route {
        override val showBottomBar: Boolean = false
        override val topBar: @Composable (() -> Unit) -> Unit = { ChangeEmailTopBar(it) }
    }

    @Serializable
    data object Warning : Route {
        override val showBottomBar: Boolean = false
        override val topBar = null
    }

    @Serializable
    data object Security : Route {
        override val showBottomBar: Boolean = false
        override val topBar: @Composable (() -> Unit) -> Unit = { SecurityTopBar(it) }
    }

    @Serializable
    data object ActiveSessions : Route {
        override val showBottomBar: Boolean = false
        override val topBar: @Composable (() -> Unit) -> Unit = { ActiveSessionsTopBar(it) }
    }

    @Serializable
    data object AboutApp : Route {
        override val showBottomBar: Boolean = false
        override val topBar: @Composable (() -> Unit) -> Unit = { AboutAppTopBar(it) }
    }
}