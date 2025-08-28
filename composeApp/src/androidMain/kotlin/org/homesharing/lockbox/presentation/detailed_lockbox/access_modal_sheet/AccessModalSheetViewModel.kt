package org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.navigation.routes.MainRoutes
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent
import kotlin.reflect.typeOf

internal class AccessModalSheetViewModel(
    private val repository: BoxesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState: MutableStateFlow<AccessModalSheetUiState> =
        MutableStateFlow(AccessModalSheetUiState())
    val uiState: StateFlow<AccessModalSheetUiState> = _uiState.asStateFlow()

    private val _tokenData: MutableSharedFlow<String> = MutableSharedFlow()
    val tokenData: SharedFlow<String> = _tokenData.asSharedFlow()

    private val macAddress: String =
        savedStateHandle.toRoute<MainRoutes.DetailedBox>(typeMap = mapOf(typeOf<String>() to NavType.StringType)).macAddress

    init {
        loadGuestList()
    }

    private fun loadGuestList() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val response = repository.getListOfAccesses(macAddress)) {
                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotLoadGuestList { loadGuestList() })

                is Response.Success -> _uiState.update { it.copy(accesses = response.data.accessList) }
            }
        }
        _uiState.update { it.copy(loading = false) }
    }

    fun addGuest() = uiState.value.selectedStartDate?.let { startDate ->
        uiState.value.selectedEndDate?.let { endDate ->
            viewModelScope.launch {
                val response = repository.createAccess(
                    macAddress = macAddress,
                    startDate = startDate,
                    startTime = uiState.value.selectedStartTime ?: 0,
                    endDate = endDate,
                    endTime = uiState.value.selectedEndTime ?: 0,
                    alias = uiState.value.guestName,
                )

                if (response is Response.Success) {
                    emitLink(response.data.id)
                }
            }
        }
    }

    fun emitLink(link: String) = viewModelScope.launch { _tokenData.emit(link) }

    fun revokeToken(tokenId: String) = viewModelScope.launch {
        repository.revokeAccess(macAddress, tokenId)
    }

    fun onChangeStartDate(newStart: Long) =
        _uiState.update { it.copy(selectedStartDate = newStart) }

    fun onChangeStartTime(newStartTime: Long) =
        _uiState.update { it.copy(selectedStartTime = newStartTime) }

    fun onChangeEndDate(newEnd: Long) = _uiState.update { it.copy(selectedEndDate = newEnd) }

    fun onChangeEndTime(newEndTime: Long) =
        _uiState.update { it.copy(selectedEndTime = newEndTime) }

    fun onChangeGuestName(newGuestName: String) = _uiState.update {
        it.copy(guestName = newGuestName)
    }

    fun toStep(step: AccessModalSheetStep) {
        if (step is AccessModalSheetStep.GuestList) {
            loadGuestList()
            _uiState.update {
                it.copy(
                    guestName = "",
                    selectedStartDate = null,
                    selectedEndDate = null
                )
            }
        }
        _uiState.update { it.copy(step = step) }
    }

    private companion object {
        fun couldNotLoadGuestList(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_load_guest_list,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )
    }
}

@Immutable
internal data class AccessModalSheetUiState(
    val step: AccessModalSheetStep = AccessModalSheetStep.GuestList,
    val accesses: List<Access> = emptyList(),
    val loading: Boolean = false,
    val guestName: String = "",
    val selectedStartDate: Long? = null,
    val selectedStartTime: Long? = null,
    val selectedEndDate: Long? = null,
    val selectedEndTime: Long? = null,
)

internal sealed interface AccessModalSheetStep {
    data object GuestList : AccessModalSheetStep
    data object AddGuest : AccessModalSheetStep
    data class GuestInfo(val access: Access) : AccessModalSheetStep
}
