package org.homesharing.lockbox.presentation.detailed_lockbox

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.box.BoxWithStatus
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.presentation.snackbar.SnackbarController
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.boxOpenedSuccessfully
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotLoadBoxInfo
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotOpenBox
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUnbindBox
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUpdateBoxAddress
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUpdateBoxName

internal class DetailedLockboxViewModelImpl(
    private val mac: String,
    private val boxesRepository: BoxesRepository,
) : DetailedLockboxViewModel() {
    override val uiState = MutableStateFlow(DetailedLockBoxUiState())

    override val unbindSuccess = MutableSharedFlow<Unit>()

    init {
        findBoxByMac()
    }

    override fun openBox() {
        viewModelScope.launch {
            when (boxesRepository.openBoxByMac(mac)) {
                is Response.Failure -> SnackbarController.receiveEvent(couldNotOpenBox { openBox() })

                is Response.Success -> SnackbarController.receiveEvent(boxOpenedSuccessfully)
            }
        }
    }

    override fun saveAddress(newAddress: String) {
        viewModelScope.launch {
            when (
                val response = boxesRepository.updateBoxAddress(
                    macAddress = mac,
                    newAddress = newAddress,
                    ownerSub = uiState.value.ownerSub
                )
            ) {
                is Response.Success -> uiState.update {
                    it.copy(
                        address = response.data.address,
                    )
                }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotUpdateBoxAddress { saveAddress(newAddress) })

            }
        }
    }

    override fun saveName(newName: String) {
        viewModelScope.launch {
            when (
                val response = boxesRepository.updateBoxName(
                    macAddress = mac,
                    newName = newName,
                    ownerSub = uiState.value.ownerSub
                )
            ) {
                is Response.Success -> uiState.update { it.copy(boxName = response.data.alias) }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotUpdateBoxName { saveName(newName) })
            }
        }
    }

    override fun unbindDevice() {
        viewModelScope.launch {
            when (boxesRepository.unbindBox(mac)) {
                is Response.Success -> unbindSuccess.emit(Unit)

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotUnbindBox { unbindDevice() })
            }
        }
    }

    private fun findBoxByMac() {
        viewModelScope.launch {
            DetailedLockBoxUiStateMapper().map(boxesRepository.getBoxByMac(mac))
        }
    }

    inner class DetailedLockBoxUiStateMapper {
        suspend fun map(from: Response<BoxWithStatus>) {
            when (from) {
                is Response.Success -> uiState.update {
                    it.copy(
                        boxName = from.data.box.alias,
                        address = from.data.box.address,
                        signalStrength = from.data.status.signalStrength?.toInt(),
                        batteryLevel = from.data.status.batteryLevel?.toInt(),
                        ownerSub = from.data.box.ownerSub,
                        isBoxOpen = from.data.status.opened ?: false,
                    )
                }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotLoadBoxInfo { findBoxByMac() })

            }
        }
    }
}
