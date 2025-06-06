package org.homesharing.lockbox.presentation.detailed_lockbox

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

internal class DetailedLockBoxViewModel(
    private val boxesRepository: BoxesRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<DetailedLockBoxUiState> =
        MutableStateFlow(DetailedLockBoxUiState())
    val uiState: StateFlow<DetailedLockBoxUiState> get() = _uiState.asStateFlow()

    fun openBox() {
        viewModelScope.launch {
            when (boxesRepository.openBoxByMac(uiState.value.macAddress)) {
                is Response.Failure -> SnackbarController.receiveEvent(couldNotOpenBox { openBox() })

                is Response.Success -> SnackbarController.receiveEvent(boxOpenedSuccessfully)
            }
        }
    }

    fun saveAddress(newAddress: String) {
        viewModelScope.launch {
            when (
                val response = boxesRepository.updateBoxAddress(
                    macAddress = uiState.value.macAddress,
                    newAddress = newAddress
                )
            ) {
                is Response.Success -> {
                    _uiState.update {
                        it.copy(
                            address = response.data.address,
                        )
                    }
                }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotUpdateAddress { saveAddress(newAddress) })

            }
        }
    }

    fun saveName(newName: String) {
        viewModelScope.launch {
            when (
                val response = boxesRepository.updateBoxName(
                    macAddress = uiState.value.macAddress,
                    newName = newName
                )
            ) {
                is Response.Success -> _uiState.update { it.copy(boxName = response.data.alias) }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotUpdateBoxName { saveName(newName) })

            }
        }
    }

    fun unbindDevice(macAddress: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (boxesRepository.unbindBox(macAddress)) {
                is Response.Success -> onSuccess()

                is Response.Failure ->
                    SnackbarController.receiveEvent(
                        couldNotUnbindBox {
                            unbindDevice(
                                macAddress,
                                onSuccess
                            )
                        }
                    )
            }
        }
    }

    fun findBoxByMac(address: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(macAddress = address) }

            when (val response = boxesRepository.getBoxByMac(address)) {
                is Response.Success -> _uiState.update {
                    it.copy(
                        boxName = response.data.alias,
                        address = response.data.address,
                        signalStrength = response.data.signalStrength.toInt(),
                        batteryLevel = response.data.batteryLevel.toInt()
                    )
                }


                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotLoadBoxInfo { findBoxByMac(uiState.value.macAddress) })

            }
        }
    }


    private companion object {
        fun couldNotLoadBoxInfo(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_load_box_info,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )

        fun couldNotUpdateAddress(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_update_box_address,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )

        fun couldNotUpdateBoxName(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_update_box_name,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )

        fun couldNotUnbindBox(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_unbind_box,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )

        fun couldNotOpenBox(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_open_box,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )

        val boxOpenedSuccessfully = SnackbarEvent(
            message = R.string.box_opened_successfully,
            action = null
        )
    }
}

internal data class DetailedLockBoxUiState(
    val macAddress: String = "",
    val boxName: String = "",
    val address: String = "",
    val signalStrength: Int = 0,
    val batteryLevel: Int = 0,
)
