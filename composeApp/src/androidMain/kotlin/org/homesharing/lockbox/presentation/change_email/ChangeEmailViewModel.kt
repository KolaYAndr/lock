package org.homesharing.lockbox.presentation.change_email

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChangeEmailViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<ChangeEmailUiState> = MutableStateFlow(ChangeEmailUiState())
    val uiState: StateFlow<ChangeEmailUiState> get() = _uiState.asStateFlow()

    fun toStep(step: ChangeEmailStep) {
        _uiState.update {
            it.copy(changeEmailStep = step)
        }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(newEmail = newEmail)
        }
    }

    fun changeEmail() {

    }
}

data class ChangeEmailUiState(
    val newEmail: String = "",
    val changeEmailStep: ChangeEmailStep = ChangeEmailStep.WantToChange
)

sealed class ChangeEmailStep {
    data object WantToChange: ChangeEmailStep()
    data object NewEmailInput: ChangeEmailStep()
    data object ConfirmNewEmail: ChangeEmailStep()
    data object EmailChangedSuccessfully: ChangeEmailStep()
}
