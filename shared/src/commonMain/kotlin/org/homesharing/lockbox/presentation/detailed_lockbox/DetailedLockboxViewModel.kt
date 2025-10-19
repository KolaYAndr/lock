package org.homesharing.lockbox.presentation.detailed_lockbox

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class DetailedLockboxViewModel : ViewModel() {
    abstract val uiState: StateFlow<DetailedLockBoxUiState>
    abstract fun setMacAddress(macAddress: String)
    abstract fun openBox()
    abstract fun saveAddress(newAddress: String)
    abstract fun saveName(newName: String)
    abstract fun unbindDevice(onSuccess: () -> Unit)
    abstract suspend fun getBoxStatus()
}

data class DetailedLockBoxUiState(
    val boxName: String = "",
    val address: String = "",
    val signalStrength: Int? = null,
    val batteryLevel: Int? = null,
    val ownerSub: String = "",
    val isBoxOpen: Boolean = false,
)
