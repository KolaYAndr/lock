package org.homesharing.lockbox.presentation.lockboxes.add_virtual_device_modal

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.presentation.snackbar_controller.CommonStringKeys
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDuration
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

internal class AddVirtualDeviceModalViewModelImpl(
    private val boxesRepository: BoxesRepository,
) : AddVirtualDeviceModalViewModel() {
    override val addVirtualDeviceUiState = MutableStateFlow(AddVirtualDeviceUiState())

    override fun onChangeDeviceName(newName: String) = addVirtualDeviceUiState.update {
        it.copy(deviceName = newName)
    }

    override fun onChangeMacAddress(newMacAddress: String) = addVirtualDeviceUiState.update {
        it.copy(macAddress = newMacAddress)
    }

    override fun onChangeDeviceAddress(newAddress: String) = addVirtualDeviceUiState.update {
        it.copy(deviceAddress = newAddress)
    }

    override fun onCreateVirtualDevice() {
        viewModelScope.launch {
            when (val codeResponse = boxesRepository.createLinkingCode()) {
                is Response.Success -> {
                    when (boxesRepository.physicalSignUp(
                        codeResponse.data.code,
                        addVirtualDeviceUiState.value.macAddress
                    )) {
                        is Response.Failure -> SnackbarController.receiveEvent(
                            couldNotCreateVirtualDevice { onCreateVirtualDevice() })

                        is Response.Success -> SnackbarController.receiveEvent(virtualDeviceCreated)
                    }
                }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotCreateVirtualDevice { onCreateVirtualDevice() })
            }
        }
    }

    private companion object {
        val virtualDeviceCreated = SnackbarEvent(
            messageKey = CommonStringKeys.VirtualDeviceCreated,
            duration = SnackbarDuration.Short
        )

        fun couldNotCreateVirtualDevice(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotCreateVirtualDevice,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = onRetry
            )
        )
    }
}
