package org.homesharing.lockbox.presentation.sign_in

import androidx.lifecycle.viewModelScope
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.homesharing.lockbox.presentation.login.EmailError

private const val INVALID_USER_CREDENTIALS = "Invalid user credentials"

internal class SignInViewModelImpl(
    private val authRepository: AuthRepository
) : SignInViewModel() {
    override val uiState = MutableStateFlow(SignInUiState())

    private val emailPattern = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")

    override fun onChangeEmail(newEmail: String) = uiState.update {
        it.copy(email = newEmail, emailError = null)
    }

    override fun onChangePassword(newPassword: String) = uiState.update {
        it.copy(password = newPassword, passwordError = null)
    }

    private fun parsePasswordError(message: String): SignInPasswordError = when (message) {
        INVALID_USER_CREDENTIALS -> SignInPasswordError.InvalidUserCredentials
        else -> SignInPasswordError.Unexpected
    }

    override fun signIn() {
        viewModelScope.launch {
            uiState.update { it.copy(emailError = null, passwordError = null) }
            if (!emailPattern.matches(uiState.value.email)) {
                uiState.update { it.copy(emailError = EmailError.DoesntMatchEmailPattern) }
            } else try {
                val response = authRepository.loadTokensInitially(
                    uiState.value.email,
                    uiState.value.password
                )
                if (response is Response.Failure) {
                    uiState.update {
                        it.copy(
                            passwordError = parsePasswordError(response.message)
                        )
                    }
                }
            } catch (_: ConnectTimeoutException) {
                uiState.update { it.copy(passwordError = SignInPasswordError.UnknownHost) }
            } catch (_: HttpRequestTimeoutException) {
                uiState.update { it.copy(passwordError = SignInPasswordError.Timeout) }
            } catch (_: Exception) {
                uiState.update { it.copy(passwordError = SignInPasswordError.Unexpected) }
            }
        }
    }
}
