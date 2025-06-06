package org.homesharing.lockbox.presentation.change_email

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.homesharing.lockbox.presentation.login.EmailError
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

internal class ChangeEmailViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<ChangeEmailUiState> =
        MutableStateFlow(ChangeEmailUiState())
    val uiState: StateFlow<ChangeEmailUiState> = _uiState.asStateFlow()
    private val emailPattern = Patterns.EMAIL_ADDRESS

    fun toStep(step: ChangeEmailStep) = _uiState.update {
        it.copy(changeEmailStep = step)
    }

    fun onEmailChange(newEmail: String) = _uiState.update {
        it.copy(newEmail = newEmail, emailError = null)
    }

    fun changeEmail() {
        if (emailPattern.matcher(uiState.value.newEmail).matches()) {
            viewModelScope.launch {
                when (repository.sendConfirmation(uiState.value.newEmail)) {
                    is Response.Failure -> SnackbarController.receiveEvent(couldNotSendLink { changeEmail() })

                    is Response.Success -> toStep(ChangeEmailStep.ConfirmNewEmail)
                }
            }
        } else _uiState.update { it.copy(emailError = EmailError.DoesntMatchEmailPattern) }
    }

    private companion object {
        fun couldNotSendLink(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_send_link_to_change_email,
            action = SnackbarAction(
                name = R.string.retry,
                effect = onRetry
            )
        )
    }
}

internal data class ChangeEmailUiState(
    val newEmail: String = "",
    val emailError: EmailError.DoesntMatchEmailPattern? = null,
    val changeEmailStep: ChangeEmailStep = ChangeEmailStep.WantToChange,
)

internal sealed class ChangeEmailStep {
    data object WantToChange : ChangeEmailStep()
    data object NewEmailInput : ChangeEmailStep()
    data object ConfirmNewEmail : ChangeEmailStep()
}
