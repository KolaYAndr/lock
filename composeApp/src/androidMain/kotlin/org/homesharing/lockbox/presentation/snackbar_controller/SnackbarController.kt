package org.homesharing.lockbox.presentation.snackbar_controller

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

internal object SnackbarController {
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun receiveEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}
