package org.homesharing.lockbox.presentation.lockboxes.connect_device_modal

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

internal class ConnectDeviceViewModel(private val boxesRepository: BoxesRepository) : ViewModel() {
    fun createTestDevice() {
        viewModelScope.launch {
            when (boxesRepository.createTestDevice()) {
                is Response.Failure -> SnackbarController.receiveEvent(couldNotCreateTestDevice { createTestDevice() })
                is Response.Success -> SnackbarController.receiveEvent(testDeviceCreated)
            }
        }
    }

    companion object {
        val testDeviceCreated = SnackbarEvent(
            message = R.string.test_device_created,
            duration = SnackbarDuration.Short
        )

        fun couldNotCreateTestDevice(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_create_test_device,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )
    }
}