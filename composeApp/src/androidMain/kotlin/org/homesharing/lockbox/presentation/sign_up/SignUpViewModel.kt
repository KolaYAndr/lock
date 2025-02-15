package org.homesharing.lockbox.presentation.sign_up

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AuthRepository

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> get() = _uiState.asStateFlow()
    private val emailMatcher = Patterns.EMAIL_ADDRESS

    fun onChangeEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail, emailError = null)
        }
    }

    fun confirmEmail() {
        //TODO
    }

    fun setPassword() {
        viewModelScope.launch {
            if (uiState.value.password == uiState.value.passwordRepeated) {
//                try {
                    val response = authRepository.signUp(
                        username = uiState.value.email,
                        password = uiState.value.password,
                        passwordRepeated = uiState.value.passwordRepeated
                    )

                    if (response.error) {
                        _uiState.update { it.copy(passwordRepeatedError = response.errorText) }
                    }
//                } catch (e: Exception) {
//                    _uiState.update { it.copy(passwordRepeatedError = e.message.toString()) }
//                }

            }
            else {
                _uiState.update { it.copy(passwordRepeatedError = "Passwords don't match") }
            }
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

    fun toStep(step: SignUpStep) {
        when(step) {
            SignUpStep.EmailConfirmation -> {
                if (emailMatcher.matcher(uiState.value.email).matches()) {
                    _uiState.update { it.copy(signUpStep = step) }
                } else _uiState.update { it.copy(emailError = "Doesn't match email pattern") }
            }
            else -> _uiState.update { it.copy(signUpStep = step) }
        }
    }
}

data class SignUpUiState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordRepeated: String = "",
    val passwordRepeatedError: String? = null,
    val isPasswordSeen: Boolean = false,
    val isPasswordRepeatedSeen: Boolean = false,
    val signUpStep: SignUpStep = SignUpStep.EmailInput,
)

sealed class SignUpStep {
    data object EmailInput : SignUpStep()
    data object EmailConfirmation : SignUpStep()
    data object PasswordInput : SignUpStep()
}
