package org.homesharing.lockbox.presentation.snackbar_controller

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val name: String,
    val effect: suspend () -> Unit,
)

object SnackbarController {
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun receiveEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}
