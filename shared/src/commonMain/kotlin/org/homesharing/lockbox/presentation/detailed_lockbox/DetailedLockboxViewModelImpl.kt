package org.homesharing.lockbox.presentation.detailed_lockbox

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
import kotlin.properties.Delegates

internal class DetailedLockboxViewModelImpl(
    private val boxesRepository: BoxesRepository,
) : DetailedLockboxViewModel() {
    override val uiState = MutableStateFlow(DetailedLockBoxUiState())
    private var mac by Delegates.notNull<String>()

    override fun setMacAddress(macAddress: String) {
        mac = macAddress
        findBoxByMac()
    }

    override fun openBox() {
        viewModelScope.launch {
            when (boxesRepository.openBoxByMac(mac)) {
                is Response.Failure -> SnackbarController.receiveEvent(couldNotOpenBox { openBox() })

                is Response.Success -> SnackbarController.receiveEvent(boxOpenedSuccessfully)
            }
            getBoxStatus()
        }
    }

    override suspend fun getBoxStatus() {
        when (val status = boxesRepository.getBoxStatusByMac(mac)) {
            is Response.Failure -> SnackbarController.receiveEvent( couldNotUpdateBoxStatus { getBoxStatus() } )
            is Response.Success -> uiState.update {
                it.copy(isBoxOpen = status.data.opened ?: false)
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
                    SnackbarController.receiveEvent(couldNotUpdateAddress { saveAddress(newAddress) })

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

    override fun unbindDevice(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (boxesRepository.unbindBox(mac)) {
                is Response.Success -> onSuccess()

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotUnbindBox { unbindDevice(onSuccess) })
            }
        }
    }

    private fun findBoxByMac() {
        viewModelScope.launch {
            when (val response = boxesRepository.getBoxByMac(mac)) {
                is Response.Success -> uiState.update {
                    it.copy(
                        boxName = response.data.box.alias,
                        address = response.data.box.address,
                        signalStrength = response.data.status.signalStrength?.toInt(),
                        batteryLevel = response.data.status.batteryLevel?.toInt(),
                        ownerSub = response.data.box.ownerSub,
                        isBoxOpen = response.data.status.opened ?: false,
                    )
                }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotLoadBoxInfo { findBoxByMac() })

            }
        }
    }

    private companion object {
        fun couldNotLoadBoxInfo(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotLoadBoxInfo,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = onRetry
            )
        )

        fun couldNotUpdateAddress(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotUpdateBoxAddress,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = onRetry
            )
        )

        fun couldNotUpdateBoxName(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotUpdateBoxName,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = onRetry
            )
        )

        fun couldNotUnbindBox(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotUnbindBox,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = onRetry
            )
        )

        fun couldNotOpenBox(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotOpenBox,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = onRetry
            )
        )

        fun couldNotUpdateBoxStatus(onRetry: suspend () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotUpdateBoxStatus,
            duration = SnackbarDuration.Long,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = onRetry
            )
        )

        val boxOpenedSuccessfully = SnackbarEvent(
            messageKey = CommonStringKeys.BoxOpenedSuccessfully,
            duration = SnackbarDuration.Short
        )
    }
}
