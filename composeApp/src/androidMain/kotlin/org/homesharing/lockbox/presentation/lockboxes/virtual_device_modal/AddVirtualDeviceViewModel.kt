package org.homesharing.lockbox.presentation.lockboxes.virtual_device_modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.BoxesRepository

internal class AddVirtualDeviceViewModel(
    val boxesRepository: BoxesRepository
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

    fun onCreateVirtualDevice() = viewModelScope.launch {

    }
}

internal data class AddVirtualDeviceUiState(
    val deviceName: String = "",
    val deviceAddress: String = "",
)
