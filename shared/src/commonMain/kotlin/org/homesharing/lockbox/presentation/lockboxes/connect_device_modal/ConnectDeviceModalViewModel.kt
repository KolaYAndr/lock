package org.homesharing.lockbox.presentation.lockboxes.connect_device_modal

import androidx.lifecycle.ViewModel

abstract class ConnectDeviceModalViewModel: ViewModel() {
    abstract fun createTestDevice()
}
