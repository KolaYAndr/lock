package org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.invitation_details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class GuestInvitationViewModel : ViewModel() {

    private val _datesState = MutableStateFlow(GuestInvitationUiState.Initial)
    val datesState = _datesState.asStateFlow()

    fun onDateRangeChanged(
        startDateMillis: Long?,
        endDateMillis: Long?,
    ) {
        _datesState.update {
            it.copy(
                startDateMillis = startDateMillis,
                endDateMillis = endDateMillis,
            )
        }
    }

    fun clearDates() {
        _datesState.update {
            it.copy(
                startDateMillis = null,
                endDateMillis = null,
            )
        }
    }
}


internal data class GuestInvitationUiState(
    val startDateMillis: Long?,
    val endDateMillis: Long?,
) {
    companion object {
        val Initial = GuestInvitationUiState(
            startDateMillis = null,
            endDateMillis = null
        )
    }
}