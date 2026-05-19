//package org.homesharing.lockbox.presentation.detailed_lockbox.viewmodel
//
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.homesharing.lockbox.domain.api.response.Response
//import org.homesharing.lockbox.domain.repository.BoxesRepository
//import org.homesharing.lockbox.presentation.snackbar.SnackbarController
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.boxOpenedSuccessfully
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotLoadBoxInfo
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotOpenBox
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUnbindBox
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUpdateBoxAddress
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUpdateBoxName
//
//internal class DetailedLockboxViewModelImpl(
//    private val mac: String,
//    private val boxesRepository: BoxesRepository,
//) : DetailedLockboxViewModel() {
//    override val uiState = MutableStateFlow(DetailedLockBoxUiState())
//
//    override val unbindSuccess = MutableSharedFlow<Unit>()
//
//    init {
//        findBoxByMac()
//    }
//
//    override fun openBox() {
//        viewModelScope.launch {
//            when (boxesRepository.openBoxByMac(mac)) {
//                is Response.Failure -> SnackbarController.receiveEvent(couldNotOpenBox { openBox() })
//
//                is Response.Success -> {
//                    uiState.update { it.copy(isBoxOpen = true) }
//                    SnackbarController.receiveEvent(boxOpenedSuccessfully)
//                }
//            }
//        }
//    }
//
//    override fun saveAddress(newAddress: String) {
//        viewModelScope.launch {
//            when (
//                val response = boxesRepository.updateBoxAddress(
//                    macAddress = mac,
//                    newAddress = newAddress,
//                    ownerSub = uiState.value.ownerSub
//                )
//            ) {
//                is Response.Success -> uiState.update {
//                    it.copy(
//                        address = response.data.address,
//                    )
//                }
//
//                is Response.Failure ->
//                    SnackbarController.receiveEvent(
//                        couldNotUpdateBoxAddress {
//                            saveAddress(
//                                newAddress
//                            )
//                        }
//                    )
//
//            }
//        }
//    }
//
//    override fun saveName(newName: String) {
//        viewModelScope.launch {
//            when (
//                val response = boxesRepository.updateBoxName(
//                    macAddress = mac,
//                    newName = newName,
//                    ownerSub = uiState.value.ownerSub
//                )
//            ) {
//                is Response.Success -> uiState.update { it.copy(boxName = response.data.alias) }
//
//                is Response.Failure ->
//                    SnackbarController.receiveEvent(couldNotUpdateBoxName { saveName(newName) })
//            }
//        }
//    }
//
//    override fun unbindDevice() {
//        viewModelScope.launch {
//            when (boxesRepository.unbindBox(mac)) {
//                is Response.Success -> unbindSuccess.emit(Unit)
//
//                is Response.Failure ->
//                    SnackbarController.receiveEvent(couldNotUnbindBox { unbindDevice() })
//            }
//        }
//    }
//
//    private fun findBoxByMac() {
//        viewModelScope.launch {
//            when (val box = boxesRepository.getBoxByMac(mac)) {
//                is Response.Success -> uiState.update {
//                    it.copy(
//                        boxName = box.data.box.alias,
//                        address = box.data.box.address,
//                        signalStrength = box.data.status.signalStrength?.toInt(),
//                        batteryLevel = box.data.status.batteryLevel?.toInt(),
//                        ownerSub = box.data.box.ownerSub,
//                        isBoxOpen = box.data.status.opened ?: false,
//                    )
//                }
//
//                is Response.Failure ->
//                    SnackbarController.receiveEvent(couldNotLoadBoxInfo { findBoxByMac() })
//
//            }
//        }
//    }
//}
