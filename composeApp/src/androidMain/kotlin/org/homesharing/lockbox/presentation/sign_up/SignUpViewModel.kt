package org.homesharing.lockbox.presentation.sign_up

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> get() = _uiState.asStateFlow()

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
            it.copy(signUpStep = SignUpStep.PasswordInput)
        }
    }

    fun toEmailConfirmationStep() {
        _uiState.update {
            it.copy(signUpStep = SignUpStep.EmailConfirmation)
        }
    }

    fun toEmailInputStep() {
        _uiState.update {
            it.copy(signUpStep = SignUpStep.EmailInput)
        }
    }
}

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val passwordRepeated: String = "",
    val isPasswordSeen: Boolean = false,
    val isPasswordRepeatedSeen: Boolean = false,
    val signUpStep: SignUpStep = SignUpStep.EmailInput,
    val totalSteps: Int = entrances
)

private val entrances = SignUpStep::class.sealedSubclasses.size
sealed class SignUpStep {
    data object EmailInput: SignUpStep()
    data object EmailConfirmation: SignUpStep()
    data object PasswordInput: SignUpStep()
}