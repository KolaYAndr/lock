package org.homesharing.lockbox.presentation.detailed_lockbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.BoxesRepository
import kotlin.properties.Delegates

class DetailedLockBoxViewModel(
    private val boxesRepository: BoxesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<DetailedLockBoxUiState> =
        MutableStateFlow(DetailedLockBoxUiState())
    val uiState: StateFlow<DetailedLockBoxUiState> get() = _uiState.asStateFlow()

    private var macAddress by Delegates.notNull<String>()

    fun openBox() {
        viewModelScope.launch {
            boxesRepository.openBoxByMac(macAddress)
        }
    }

    fun saveAddress(newAddress: String) {
        viewModelScope.launch {
            val response = boxesRepository.updateBoxAddress(
                macAddress = macAddress,
                newAddress = newAddress
            )

            if (response is Response.Success) _uiState.update {
                it.copy(
                    address = response.data.address,
                )
            }
        }
    }

    fun saveName(newName: String) {
        viewModelScope.launch {
            val response = boxesRepository.updateBoxName(
                macAddress = macAddress,
                newName = newName
            )

            if (response is Response.Success) _uiState.update {
                it.copy(
                    boxName = response.data.alias,
                )
            }
        }
    }

    fun unbindDevice(macAddress: String) {
        viewModelScope.launch {
            boxesRepository.unbindBox(macAddress)
        }
    }

    fun findBoxByMac(address: String) {
        viewModelScope.launch {
            macAddress = address
            val response = boxesRepository.getBoxByMac(address)
            if (response is Response.Success) _uiState.update {
                it.copy(
                    boxName = response.data.alias,
                    address = response.data.address,
                    signalStrength = response.data.signalStrength.toInt(),
                    batteryLevel = response.data.batteryLevel.toInt()
                )
            }
        }
    }
}

data class DetailedLockBoxUiState(
    val boxName: String = "",
    val address: String = "",
    val signalStrength: Int = 0,
    val batteryLevel: Int = 0
)
