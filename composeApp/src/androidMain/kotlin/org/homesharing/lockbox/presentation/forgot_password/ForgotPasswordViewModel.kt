package org.homesharing.lockbox.presentation.forgot_password

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

private const val STEP_ONE = 1
private const val STEP_TWO = 2
private const val STEP_THREE = 3
sealed class ForgotPasswordStep(val index: Int) {
    data object EmailInput : ForgotPasswordStep(STEP_ONE)
    data object EmailConfirmation : ForgotPasswordStep(STEP_TWO)
    data object PasswordInput : ForgotPasswordStep(STEP_THREE)
}
