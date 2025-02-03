package org.homesharing.lockbox.presentation.forgot_password

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.homesharing.lockbox.presentation.sign_up.SignUpStep

class ForgotPasswordViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<ForgotPasswordUiState> =
        MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> get() = _uiState.asStateFlow()
    private val emailPattern = Patterns.EMAIL_ADDRESS

    fun onChangeEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail, emailError = null)
        }
    }

    fun confirmEmail() {
        //TODO
    }

    fun setPassword() {
        if (uiState.value.password != uiState.value.passwordRepeated) {
            _uiState.update { it.copy(passwordRepeatedError = "Passwords don't match") }
        }
    }
    fun onChangePassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun onChangePasswordRepeat(newRepeatedPassword: String) {
        _uiState.update {
            it.copy(passwordRepeated = newRepeatedPassword, passwordRepeatedError = null)
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

    fun toStep(step: ForgotPasswordStep) {
        when(step) {
            ForgotPasswordStep.EmailConfirmation -> {
                if (emailPattern.matcher(uiState.value.email).matches()) {
                    _uiState.update { it.copy(step = step) }
                } else _uiState.update { it.copy(emailError = "Doesn't match email pattern") }
            }
            else -> _uiState.update { it.copy(step = step) }
        }
    }
}

data class ForgotPasswordUiState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordRepeated: String = "",
    val passwordRepeatedError: String? = null,
    val isPasswordSeen: Boolean = false,
    val isPasswordRepeatedSeen: Boolean = false,
    val step: ForgotPasswordStep = ForgotPasswordStep.EmailInput,
)

sealed class ForgotPasswordStep {
    data object EmailInput : ForgotPasswordStep()
    data object EmailConfirmation : ForgotPasswordStep()
    data object PasswordInput : ForgotPasswordStep()
}
