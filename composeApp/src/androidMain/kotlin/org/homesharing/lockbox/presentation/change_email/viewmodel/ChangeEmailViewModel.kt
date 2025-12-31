package org.homesharing.lockbox.presentation.change_email.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

internal abstract class ChangeEmailViewModel: ViewModel() {
    abstract val uiState: StateFlow<ChangeEmailUiState>
    abstract fun toStep(step: ChangeEmailStep)
    abstract fun onEmailChange(newEmail: String)
    abstract fun changeEmail()
}

internal data class ChangeEmailUiState(
    val newEmail: String = "",
    val emailError: Boolean = false,
    val changeEmailStep: ChangeEmailStep = ChangeEmailStep.WantToChange,
)

internal sealed class ChangeEmailStep {
    data object WantToChange : ChangeEmailStep()
    data object NewEmailInput : ChangeEmailStep()
    data object ConfirmNewEmail : ChangeEmailStep()
}
