package org.homesharing.lockbox.presentation.change_email

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChangeEmailViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<ChangeEmailUiState> =
        MutableStateFlow(ChangeEmailUiState())
    val uiState: StateFlow<ChangeEmailUiState> get() = _uiState.asStateFlow()
    private val emailPattern = Patterns.EMAIL_ADDRESS

    fun toStep(step: ChangeEmailStep) {
        _uiState.update {
            it.copy(changeEmailStep = step)
        }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(newEmail = newEmail, emailError = null)
        }
    }

    fun changeEmail() {
        if (emailPattern.matcher(uiState.value.newEmail).matches()) {
            //TODO
            toStep(ChangeEmailStep.ConfirmNewEmail)
        } else _uiState.update { it.copy(emailError = "Doesn't match email pattern") }
    }
}

data class ChangeEmailUiState(
    val newEmail: String = "",
    val emailError: String? = null,
    val changeEmailStep: ChangeEmailStep = ChangeEmailStep.WantToChange
)

sealed class ChangeEmailStep {
    data object WantToChange : ChangeEmailStep()
    data object NewEmailInput : ChangeEmailStep()
    data object ConfirmNewEmail : ChangeEmailStep()
}
