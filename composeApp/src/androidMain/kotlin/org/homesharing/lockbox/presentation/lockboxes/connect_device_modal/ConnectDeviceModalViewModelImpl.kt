package org.homesharing.lockbox.presentation.lockboxes.connect_device_modal

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.presentation.snackbar.SnackbarController
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotCreateTestDevice
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.testDeviceCreated

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
}
