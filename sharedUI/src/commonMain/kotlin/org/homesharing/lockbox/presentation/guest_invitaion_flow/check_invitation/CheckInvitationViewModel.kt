package org.homesharing.lockbox.presentation.guest_invitaion_flow.check_invitation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.homesharing.lockbox.domain.model.check_in_setup.CheckInSetup

internal class CheckInvitationViewModel : ViewModel() {
    private val _checkInvitation = MutableStateFlow(CheckInvitationUiState.Initial)
    val checkInvitation = _checkInvitation.asStateFlow()
}

internal data class CheckInvitationUiState(
    val guestName: String,
    val guestEmail: String,
    val buildingName: String,
    val buildingAddress: String,
    val checkInDate: String,
    val checkOutDate: String,
    val checkInTime: String,
    val checkOutTime: String,
    val guestCount: Int,
    val deposit: Int,
    val selectedSettings: List<CheckInSetup>,
    val commentForGuest: String,
) {
    companion object {
        val Initial = CheckInvitationUiState(
            guestName = "",
            guestEmail = "",
            buildingName = "",
            buildingAddress = "",
            checkInDate = "",
            checkOutDate = "",
            checkInTime = "",
            checkOutTime = "",
            guestCount = 0,
            deposit = 0,
            selectedSettings = emptyList(),
            commentForGuest = ""
        )
    }
}