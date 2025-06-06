package org.homesharing.lockbox.presentation.sign_up

import android.util.Patterns
import androidx.annotation.StringRes
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

private const val USER_EXISTS_STRING = "{\"errorMessage\":\"User exists with same email\"}"
private const val PASSWORDS_DONT_MATCH_STRING = "web.exchange.bind.exception"

internal class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    private val emailMatcher = Patterns.EMAIL_ADDRESS

    fun onChangeEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail, emailError = null)
        }
    }

    suspend fun checkIfConfirmed() = try {
        authRepository.loadTokensInitially(
            uiState.value.email,
            uiState.value.password
        )
    } catch (e: Exception) {
    }

    fun signUp() = viewModelScope.launch {
        if (uiState.value.password != uiState.value.passwordRepeated) {
            _uiState.update { it.copy(passwordRepeatedError = PasswordRepeatedError.PasswordsDontMatch) }
        } else when (
            val response = authRepository.signUp(
                uiState.value.email,
                uiState.value.password,
                uiState.value.passwordRepeated
            )
        ) {
            is Response.Failure -> {
                _uiState.update {
                    it.copy(
                        passwordRepeatedError = parsePasswordRepeatedError(response.message)
                    )
                }
            }

            is Response.Success -> {
                toStep(SignUpStep.EmailConfirmation)
            }
        }
    }

    private fun parsePasswordRepeatedError(message: String): PasswordRepeatedError = when (message) {
            USER_EXISTS_STRING -> PasswordRepeatedError.UserExists
            PASSWORDS_DONT_MATCH_STRING -> PasswordRepeatedError.PasswordsDontMatch
            else -> PasswordRepeatedError.Unexpected
        }

    fun sendEmailConfirmation() = viewModelScope.launch {
        authRepository.sendConfirmation(uiState.value.email)
    }

    fun onChangePassword(newPassword: String) = _uiState.update {
        it.copy(password = newPassword)
    }


    fun onChangePasswordRepeat(newRepeatedPassword: String) = _uiState.update {
        it.copy(
            passwordRepeated = newRepeatedPassword,
            passwordRepeatedError = PasswordRepeatedError.None
        )
    }

    fun onPasswordSeenChange() = _uiState.update {
        it.copy(isPasswordSeen = !it.isPasswordSeen)
    }

    fun onPasswordRepeatedSeenChange() = _uiState.update {
        it.copy(isPasswordRepeatedSeen = !it.isPasswordRepeatedSeen)
    }

    fun toStep(nextStep: SignUpStep) = when (nextStep) {
        SignUpStep.PasswordInput -> if (emailMatcher.matcher(uiState.value.email).matches()) {
            _uiState.update { it.copy(signUpStep = nextStep) }
        } else _uiState.update { it.copy(emailError = EmailError.DoesntMatchEmailPattern) }

        else -> _uiState.update { it.copy(signUpStep = nextStep) }
    }
}

internal data class SignUpUiState(
    val email: String = "",
    val emailError: EmailError? = null,
    val password: String = "",
    val passwordRepeated: String = "",
    val passwordRepeatedError: PasswordRepeatedError = PasswordRepeatedError.None,
    val isPasswordSeen: Boolean = false,
    val isPasswordRepeatedSeen: Boolean = false,
    val signUpStep: SignUpStep = SignUpStep.EmailInput,
)


private const val STEP_ONE = 1
private const val STEP_TWO = 2
private const val STEP_THREE = 3

internal sealed class SignUpStep(val index: Int) {
    data object EmailInput : SignUpStep(STEP_ONE)
    data object PasswordInput : SignUpStep(STEP_TWO)
    data object EmailConfirmation : SignUpStep(STEP_THREE)
}

internal sealed class PasswordRepeatedError(@StringRes val messageId: Int?) {
    data object None : PasswordRepeatedError(null)
    data object PasswordsDontMatch : PasswordRepeatedError(R.string.password_dont_match)
    data object UserExists : PasswordRepeatedError(R.string.user_already_exists)
    data object Unexpected : PasswordRepeatedError(R.string.unknown_error)
}
