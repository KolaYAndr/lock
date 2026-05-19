package org.homesharing.lockbox.presentation.guest_invitaion_flow.check_in_setup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.homesharing.lockbox.domain.model.check_in_setup.CheckInSetup

internal class CheckInViewModel : ViewModel() {
    private val _checkInState = MutableStateFlow(CheckInUiState.Initial)
    val checkInState = _checkInState.asStateFlow()

    fun onCheck(item: CheckInSetup) {
        _checkInState.update {
            val selected = it.selected
            it.copy(selected = if (item in selected) selected - item else selected + item)
        }
    }

    fun onChangeComment(newComment: String) {
        _checkInState.update {
            it.copy(commentForGuest = newComment)
        }
    }
}

internal data class CheckInUiState(
    val checkInOptions: List<CheckInSetup>,
    val selected: List<CheckInSetup>,
    val commentForGuest: String,
) {
    companion object {
        val Initial = CheckInUiState(
            checkInOptions = CheckInSetup.entries,
            selected = CheckInSetup.entries,
            commentForGuest = ""
        )
    }
}