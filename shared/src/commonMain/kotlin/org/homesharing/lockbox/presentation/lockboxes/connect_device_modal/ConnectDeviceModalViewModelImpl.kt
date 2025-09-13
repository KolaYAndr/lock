package org.homesharing.lockbox.presentation.lockboxes.connect_device_modal

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.presentation.snackbar_controller.CommonStringKeys
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDuration
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

internal class ConnectDeviceModalViewModelImpl(
    private val boxesRepository: BoxesRepository
) : ConnectDeviceModalViewModel() {
    override fun createTestDevice() {
        viewModelScope.launch {
            when (boxesRepository.createTestDevice()) {
                is Response.Failure -> SnackbarController.receiveEvent(couldNotCreateTestDevice { createTestDevice() })
                is Response.Success -> SnackbarController.receiveEvent(testDeviceCreated)
            }
        }
    }

    companion object {
        val testDeviceCreated = SnackbarEvent(
            messageKey = CommonStringKeys.TestDeviceCreated,
            duration = SnackbarDuration.Short
        )

        fun couldNotCreateTestDevice(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotCreateTestDevice,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = onRetry
            )
        )
    }
}
