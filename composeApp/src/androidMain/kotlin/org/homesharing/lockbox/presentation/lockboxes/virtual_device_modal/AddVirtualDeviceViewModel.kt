package org.homesharing.lockbox.presentation.lockboxes.virtual_device_modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

internal class AddVirtualDeviceViewModel(
    private val boxesRepository: BoxesRepository,
) : ViewModel() {
    private val _addVirtualDeviceUiState: MutableStateFlow<AddVirtualDeviceUiState> =
        MutableStateFlow(AddVirtualDeviceUiState())
    val addVirtualDeviceUiState: StateFlow<AddVirtualDeviceUiState> =
        _addVirtualDeviceUiState.asStateFlow()

    fun onChangeDeviceName(newName: String) = _addVirtualDeviceUiState.update {
        it.copy(deviceName = newName)
    }

    fun onChangeDeviceAddress(newAddress: String) = _addVirtualDeviceUiState.update {
        it.copy(deviceAddress = newAddress)
    }

    fun onCreateVirtualDevice() {
        viewModelScope.launch {
            val codeResponse = boxesRepository.createLinkingCode()

            when (codeResponse) {
                is Response.Success -> {
                    val boxLinking = boxesRepository.boxLinking(

                    )
                }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotCreateVirtualDevice { onCreateVirtualDevice() })
            }
        }
    }

    private companion object {
        fun couldNotCreateVirtualDevice(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_create_virtual_device,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )
    }
}

internal data class AddVirtualDeviceUiState(
    val deviceName: String = "",
    val deviceAddress: String = "",
)
