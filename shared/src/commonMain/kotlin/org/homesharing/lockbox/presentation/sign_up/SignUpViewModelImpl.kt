package org.homesharing.lockbox.presentation.sign_up

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.homesharing.lockbox.presentation.login.EmailError

private const val USER_EXISTS_STRING = "{\"errorMessage\":\"User exists with same email\"}"
private const val PASSWORDS_DONT_MATCH_STRING = "web.exchange.bind.exception"

class SignUpViewModelImpl(
    private val authRepository: AuthRepository,
) : SignUpViewModel() {
    override val uiState = MutableStateFlow(SignUpUiState())

    private val emailPattern = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")

    override fun onChangeEmail(newEmail: String) {
        uiState.update {
            it.copy(email = newEmail, emailError = null)
        }
    }

    override fun onChangePassword(newPassword: String) = uiState.update {
        it.copy(password = newPassword)
    }


    override fun onChangePasswordRepeat(newRepeatedPassword: String) = uiState.update {
        it.copy(
            passwordRepeated = newRepeatedPassword,
            passwordRepeatedError = null
        )
    }

    override fun sendEmailConfirmation() {
        viewModelScope.launch {
            authRepository.sendConfirmation(uiState.value.email)
        }
    }

    override suspend fun checkIfConfirmed() {
        try {
            authRepository.loadTokensInitially(
                uiState.value.email,
                uiState.value.password
            )
        } catch (_: Exception) { }
    }

    override fun signUp() {
        viewModelScope.launch {
            if (uiState.value.password != uiState.value.passwordRepeated) {
                uiState.update { it.copy(passwordRepeatedError = PasswordRepeatedError.PasswordsDontMatch) }
            } else when (
                val response = authRepository.signUp(
                    uiState.value.email,
                    uiState.value.password,
                    uiState.value.passwordRepeated
                )
            ) {
                is Response.Failure -> uiState.update {
                    it.copy(
                        passwordRepeatedError = parsePasswordRepeatedError(response.message)
                    )
                }


                is Response.Success -> toStep(SignUpStep.EmailConfirmation)
            }
        }
    }


    private fun parsePasswordRepeatedError(message: String): PasswordRepeatedError =
        when (message) {
            USER_EXISTS_STRING -> PasswordRepeatedError.UserExists
            PASSWORDS_DONT_MATCH_STRING -> PasswordRepeatedError.PasswordsDontMatch
            else -> PasswordRepeatedError.Unexpected
        }

    override fun toStep(nextStep: SignUpStep) = when (nextStep) {
        SignUpStep.PasswordInput -> if (emailPattern.matches(uiState.value.email))
            uiState.update { it.copy(signUpStep = nextStep) }
        else uiState.update { it.copy(emailError = EmailError.DoesntMatchEmailPattern) }

        else -> uiState.update { it.copy(signUpStep = nextStep) }
    }
}
