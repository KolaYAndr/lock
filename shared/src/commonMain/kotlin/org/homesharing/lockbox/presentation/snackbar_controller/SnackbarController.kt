package org.homesharing.lockbox.presentation.snackbar_controller

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

object SnackbarController {
    private val _events: Channel<SnackbarEvent> = Channel()

    val events: Flow<SnackbarEvent> = _events.receiveAsFlow()

    suspend fun receiveEvent(event: SnackbarEvent) = _events.send(event)
}
