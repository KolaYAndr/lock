package org.homesharing.lockbox.presentation.lockboxes.virtual_device_modal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

internal abstract class AddVirtualDeviceModalViewModel : ViewModel() {
    abstract val addVirtualDeviceUiState: StateFlow<AddVirtualDeviceUiState>
    abstract fun onChangeDeviceName(newName: String)
    abstract fun onChangeMacAddress(newMacAddress: String)
    abstract fun onChangeDeviceAddress(newAddress: String)
    abstract fun onCreateVirtualDevice()
}

internal data class AddVirtualDeviceUiState(
    val deviceName: String = "",
    val deviceAddress: String = "",
    val macAddress: String = "",
)
