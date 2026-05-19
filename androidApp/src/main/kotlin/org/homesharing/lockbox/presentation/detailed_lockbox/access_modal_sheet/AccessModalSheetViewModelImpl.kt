//package org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet
//
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.homesharing.lockbox.domain.api.response.Response
//import org.homesharing.lockbox.domain.repository.BoxesRepository
//import org.homesharing.lockbox.presentation.snackbar.SnackbarController
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotLoadGuestList
//
//internal class AccessModalSheetViewModelImpl(
//    private val repository: BoxesRepository,
//    private val mac: String,
//) : AccessModalSheetViewModel() {
//    override val uiState = MutableStateFlow(AccessModalSheetUiState())
//    override val tokenData = MutableSharedFlow<String>()
//
//    init {
//        loadGuestList()
//    }
//
//    private fun loadGuestList() {
//        uiState.update { it.copy(loading = true) }
//        viewModelScope.launch {
//            when (val response = repository.getListOfAccesses(mac)) {
//                is Response.Failure ->
//                    SnackbarController.receiveEvent(couldNotLoadGuestList { loadGuestList() })
//
//                is Response.Success -> uiState.update { it.copy(accesses = response.data.accessList) }
//            }
//        }
//        uiState.update { it.copy(loading = false) }
//    }
//
//    override fun addGuest() {
//        uiState.value.selectedStartDate?.let { startDate ->
//            uiState.value.selectedEndDate?.let { endDate ->
//                viewModelScope.launch {
//                    val response = repository.createAccess(
//                        macAddress = mac,
//                        startDate = startDate,
//                        startTime = uiState.value.selectedStartTime ?: 0,
//                        endDate = endDate,
//                        endTime = uiState.value.selectedEndTime ?: 0,
//                        alias = uiState.value.guestName,
//                    )
//
//                    if (response is Response.Success) {
//                        emitLink(response.data.id)
//                    }
//                }
//            }
//        }
//    }
//
//    override fun emitLink(link: String) {
//        viewModelScope.launch { tokenData.emit(link) }
//    }
//
//    override fun revokeToken(tokenId: String) {
//        viewModelScope.launch {
//            repository.revokeAccess(mac, tokenId)
//        }
//    }
//
//    override fun onChangeStartDate(newStart: Long) =
//        uiState.update { it.copy(selectedStartDate = newStart) }
//
//    override fun onChangeStartTime(newStartTime: Long) =
//        uiState.update { it.copy(selectedStartTime = newStartTime) }
//
//    override fun onChangeEndDate(newEnd: Long) =
//        uiState.update { it.copy(selectedEndDate = newEnd) }
//
//    override fun onChangeEndTime(newEndTime: Long) =
//        uiState.update { it.copy(selectedEndTime = newEndTime) }
//
//    override fun onChangeGuestName(newGuestName: String) = uiState.update {
//        it.copy(guestName = newGuestName)
//    }
//
//    override fun toStep(step: AccessModalSheetStep) {
//        if (step is AccessModalSheetStep.GuestList) {
//            loadGuestList()
//            uiState.update {
//                it.copy(
//                    guestName = "",
//                    selectedStartDate = null,
//                    selectedEndDate = null
//                )
//            }
//        }
//        uiState.update { it.copy(step = step) }
//    }
//}
