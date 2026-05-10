package org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.guest_info

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class GuestInfoViewModel : ViewModel() {
    private val _guestInfo = MutableStateFlow(GuestInfoUiState.Initial)
    val guestInfo = _guestInfo.asStateFlow()

    fun updateEmail(newEmail: String) {
        _guestInfo.update { it.copy(email = newEmail) }
    }

    fun updateFullName(newFullName: String) {
        _guestInfo.update { it.copy(fullName = newFullName) }
    }
}

internal data class GuestInfoUiState(
    val email: String,
    val fullName: String,
) {
    companion object {
        val Initial = GuestInfoUiState(
            email = "",
            fullName = "",
        )
    }
}