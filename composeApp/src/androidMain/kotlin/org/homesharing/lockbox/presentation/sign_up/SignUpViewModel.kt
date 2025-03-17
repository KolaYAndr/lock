package org.homesharing.lockbox.presentation.sign_up

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
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

    suspend fun checkIfConfirmed() {
        authRepository.loadTokensInitially(
            uiState.value.email,
            uiState.value.password
        )
    }

    fun signUp() {
        viewModelScope.launch {
            when (
                val response = authRepository.signUp(
                    uiState.value.email,
                    uiState.value.password,
                    uiState.value.passwordRepeated
                )
            ) {
                is Response.Failure -> {
                    _uiState.update { it.copy(passwordRepeatedError = response.message) }
                }

                is Response.Success -> { toStep(SignUpStep.EmailConfirmation) }
            }
        }
    }

    fun sendEmailConfirmation() {
        viewModelScope.launch {
            authRepository.sendConfirmation(uiState.value.email)
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

    fun toStep(nextStep: SignUpStep) {
        when (nextStep) {
            SignUpStep.PasswordInput -> {
                if (emailMatcher.matcher(uiState.value.email).matches()) {
                    _uiState.update { it.copy(signUpStep = nextStep) }
                } else _uiState.update { it.copy(emailError = "Doesn't match email pattern") }
            }

            SignUpStep.EmailConfirmation -> {
                if (uiState.value.password != uiState.value.passwordRepeated) {
                    _uiState.update { it.copy(passwordRepeated = "Passwords don't match") }
                } else {
                    _uiState.update { it.copy(signUpStep = nextStep) }
                }
            }

            else -> {
                _uiState.update { it.copy(signUpStep = nextStep) }
            }
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
