package org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.invitation_details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class GuestInvitationViewModel : ViewModel() {

    private val _guestInvitation = MutableStateFlow(GuestInvitationUiState.Initial)
    val guestInvitation = _guestInvitation.asStateFlow()

    fun onIntent(intent: GuestInvitationIntent) {
        when (intent) {
            is GuestInvitationIntent.DateRangeChanged -> {
                onDateRangeChanged(
                    startDateMillis = intent.startDateMillis,
                    endDateMillis = intent.endDateMillis,
                )
            }

            GuestInvitationIntent.ClearDates -> {
                clearDates()
            }

            is GuestInvitationIntent.CheckInTimeChanged -> {
                onCheckInTimeChanged(intent.value)
            }

            is GuestInvitationIntent.CheckOutTimeChanged -> {
                onCheckOutTimeChanged(intent.value)
            }

            is GuestInvitationIntent.GuestNumberChanged -> {
                onGuestNumberChanged(intent.value)
            }

            is GuestInvitationIntent.DepositChanged -> {
                onDepositChanged(intent.value)
            }

            is GuestInvitationIntent.PrivateCommentChanged -> {
                onPrivateCommentChanged(intent.value)
            }

            GuestInvitationIntent.ProceedClicked -> {
                onProceedClicked()
            }
        }
    }

    private fun onProceedClicked() {
        val state = _guestInvitation.value

        val checkInTimeError = state.checkInTime == null
        val checkOutTimeError = state.checkOutTime == null
        val guestError = state.guestNumber == null

        _guestInvitation.update {
            it.copy(
                checkInTimeError = checkInTimeError,
                checkOutTimeError = checkOutTimeError,
                guestError = guestError,
            )
        }

        val hasError = checkInTimeError ||
                checkOutTimeError ||
                guestError ||
                state.startDateMillis == null ||
                state.endDateMillis == null

        if (hasError) return

        // Пока ничего больше не делаем
    }

    private fun onDateRangeChanged(
        startDateMillis: Long?,
        endDateMillis: Long?,
    ) {
        _guestInvitation.update {
            it.copy(
                startDateMillis = startDateMillis,
                endDateMillis = endDateMillis,
            )
        }
    }

    private fun clearDates() {
        _guestInvitation.update {
            it.copy(
                startDateMillis = null,
                endDateMillis = null,
            )
        }
    }

    private fun onCheckInTimeChanged(
        value: Long?,
    ) {
        _guestInvitation.update {
            it.copy(
                checkInTime = value,
                checkInTimeError = false
            )
        }
    }

    private fun onCheckOutTimeChanged(
        value: Long?,
    ) {
        _guestInvitation.update {
            it.copy(
                checkOutTime = value,
                checkOutTimeError = false
            )
        }
    }

    private fun onGuestNumberChanged(value: String) {
        _guestInvitation.update {
            it.copy(
                guestNumber = value.toInt(),
                guestError = false
            )
        }
    }

    private fun onDepositChanged(value: String) {
        _guestInvitation.update {
            it.copy(
                deposit = value.toInt(),
            )
        }
    }

    private fun onPrivateCommentChanged(value: String) {
        _guestInvitation.update {
            it.copy(
                privateComment = value,
            )
        }
    }
}

internal data class GuestInvitationUiState(
    val startDateMillis: Long?,
    val endDateMillis: Long?,
    val checkInTime: Long?,
    val checkOutTime: Long?,
    val guestNumber: Int?,
    val guestError: Boolean,
    val deposit: Int?,
    val privateComment: String,
    val checkInTimeError: Boolean,
    val checkOutTimeError: Boolean,
) {
    val isProceedEnabled: Boolean
        get() = startDateMillis != null &&
                endDateMillis != null &&
                checkInTime != null &&
                checkOutTime != null &&
                guestNumber != null &&
                !guestError &&
                !checkInTimeError &&
                !checkOutTimeError

    companion object {
        val Initial = GuestInvitationUiState(
            startDateMillis = null,
            endDateMillis = null,
            checkInTime = null,
            checkOutTime = null,
            guestNumber = null,
            deposit = null,
            privateComment = "",
            checkInTimeError = false,
            checkOutTimeError = false,
            guestError = false,

        )
    }
}

internal sealed interface GuestInvitationIntent {
    data class DateRangeChanged(
        val startDateMillis: Long?,
        val endDateMillis: Long?,
    ) : GuestInvitationIntent

    data object ClearDates : GuestInvitationIntent

    data class CheckInTimeChanged(
        val value: Long?,
    ) : GuestInvitationIntent

    data class CheckOutTimeChanged(
        val value: Long?,
    ) : GuestInvitationIntent

    data class GuestNumberChanged(
        val value: String,
    ) : GuestInvitationIntent

    data class DepositChanged(
        val value: String,
    ) : GuestInvitationIntent

    data class PrivateCommentChanged(
        val value: String,
    ) : GuestInvitationIntent

    data object ProceedClicked : GuestInvitationIntent
}