package org.homesharing.lockbox.presentation.change_email

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.homesharing.lockbox.presentation.login.EmailError

abstract class ChangeEmailViewModel: ViewModel() {
    abstract val uiState: StateFlow<ChangeEmailUiState>
    abstract fun toStep(step: ChangeEmailStep)
    abstract fun onEmailChange(newEmail: String)
    abstract fun changeEmail()
}

data class ChangeEmailUiState(
    val newEmail: String = "",
    val emailError: EmailError.DoesntMatchEmailPattern? = null,
    val changeEmailStep: ChangeEmailStep = ChangeEmailStep.WantToChange,
)

sealed class ChangeEmailStep {
    data object WantToChange : ChangeEmailStep()
    data object NewEmailInput : ChangeEmailStep()
    data object ConfirmNewEmail : ChangeEmailStep()
}
