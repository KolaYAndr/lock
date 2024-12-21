package org.homesharing.lockbox.presentation.forgot_password

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotPasswordViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<ForgotPasswordUiState> =
        MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> get() = _uiState.asStateFlow()

    fun onChangeEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun confirmEmail() {
        //TODO
    }

    fun setPassword() {
        //TODO
    }

    fun onChangePassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun onChangePasswordRepeat(newRepeatedPassword: String) {
        _uiState.update {
            it.copy(passwordRepeated = newRepeatedPassword)
        }
    }

    fun onPasswordSeenChange() {
        _uiState.update {
            it.copy(isPasswordSeen = !it.isPasswordSeen)
        }
    }

    fun onPasswordRepeatedSeenChange() {
        _uiState.update {
            it.copy(isPasswordRepeatedSeen = !it.isPasswordRepeatedSeen)
        }
    }

    fun toPasswordInputStep() {
        _uiState.update {
            it.copy(step = ForgotPasswordStep.PasswordInput)
        }
    }

    fun toEmailConfirmationStep() {
        _uiState.update {
            it.copy(step = ForgotPasswordStep.EmailConfirmation)
        }
    }

    fun toEmailInputStep() {
        _uiState.update {
            it.copy(step = ForgotPasswordStep.EmailInput)
        }
    }

}

data class ForgotPasswordUiState(
    val email: String = "",
    val password: String = "",
    val passwordRepeated: String = "",
    val isPasswordSeen: Boolean = false,
    val isPasswordRepeatedSeen: Boolean = false,
    val step: ForgotPasswordStep = ForgotPasswordStep.EmailInput,
)

sealed class ForgotPasswordStep {
    data object EmailInput : ForgotPasswordStep()
    data object EmailConfirmation : ForgotPasswordStep()
    data object PasswordInput : ForgotPasswordStep()
}
