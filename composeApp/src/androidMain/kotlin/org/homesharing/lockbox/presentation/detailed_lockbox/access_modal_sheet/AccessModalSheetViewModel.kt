package org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

internal class AccessModalSheetViewModel(
    private val repository: BoxesRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<AccessModalSheetUiState> =
        MutableStateFlow(AccessModalSheetUiState())
    val uiState: StateFlow<AccessModalSheetUiState> = _uiState.asStateFlow()

    fun loadGuestList(mac: String) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            _uiState.update { it.copy(macAddress = mac) }
            when (val response = repository.getListOfAccesses(uiState.value.macAddress)) {
                is Response.Failure -> {
                    SnackbarController.receiveEvent(couldNotLoadGuestList { loadGuestList(uiState.value.macAddress) })
                }

                is Response.Success -> _uiState.update {
                    it.copy(accesses = response.data.accessList)
                }
            }
        }
        _uiState.update { it.copy(loading = false) }
    }

    fun addGuest() {}

    fun toStep(step: AccessModalSheetStep) = _uiState.update { it.copy(step = step) }

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
    val macAddress: String = "",
    val step: AccessModalSheetStep = AccessModalSheetStep.GuestList,
    val accesses: List<Access> = emptyList(),
    val loading: Boolean = false,
)

internal sealed interface AccessModalSheetStep {
    data object GuestList: AccessModalSheetStep
    data object AddGuest: AccessModalSheetStep
}