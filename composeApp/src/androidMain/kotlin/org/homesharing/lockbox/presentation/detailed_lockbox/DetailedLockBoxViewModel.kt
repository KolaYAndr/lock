package org.homesharing.lockbox.presentation.detailed_lockbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

            _uiState.update {
                it.copy(
                    address = response.body.address,
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

            _uiState.update {
                it.copy(
                    boxName = response.body.alias,
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
            _uiState.update {
                it.copy(
                    boxName = response.body.alias,
                    address = response.body.address,
                    signalStrength = response.body.signalStrength.toInt(),
                    batteryLevel = response.body.batteryLevel.toInt()
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
