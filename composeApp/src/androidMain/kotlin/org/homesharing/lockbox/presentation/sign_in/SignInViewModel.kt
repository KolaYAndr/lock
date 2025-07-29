package org.homesharing.lockbox.presentation.sign_in

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.homesharing.lockbox.presentation.login.EmailError
import java.net.UnknownHostException

private const val INVALID_USER_CREDENTIALS = "Invalid user credentials"

internal class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()
    private val emailPattern = Patterns.EMAIL_ADDRESS

    fun onChangeEmail(newEmail: String) = _uiState.update {
        it.copy(email = newEmail, emailError = null)
    }

    fun onChangePassword(newPassword: String) = _uiState.update {
        it.copy(password = newPassword, passwordError = null)
    }

    private fun parsePasswordError(message: String): SignInPasswordError = when (message) {
        INVALID_USER_CREDENTIALS -> SignInPasswordError.InvalidUserCredentials
        else -> SignInPasswordError.Unexpected
    }

    fun signIn() = viewModelScope.launch {
        _uiState.update { it.copy(emailError = null, passwordError = null) }
        if (!emailPattern.matcher(uiState.value.email).matches()) {
            _uiState.update { it.copy(emailError = EmailError.DoesntMatchEmailPattern) }
        } else try {
            val response = authRepository.loadTokensInitially(
                uiState.value.email,
                uiState.value.password
            )

            response.error?.let {
                _uiState.update {
                    it.copy(
                        passwordError = parsePasswordError(response.errorDescription!!)
                    )
                }
            }

        } catch (e: UnknownHostException) {
            _uiState.update { it.copy(passwordError = SignInPasswordError.UnknownHost) }
        } catch (e: HttpRequestTimeoutException) {
            _uiState.update { it.copy(passwordError = SignInPasswordError.Timeout) }
        } catch (e: Exception) {
            _uiState.update { it.copy(passwordError = SignInPasswordError.Unexpected) }
        }
    }
}

internal data class SignInUiState(
    val email: String = "",
    val emailError: EmailError? = null,
    val password: String = "",
    val passwordError: SignInPasswordError? = null,
)

internal sealed interface SignInPasswordError {
    data object InvalidUserCredentials : SignInPasswordError
    data object Unexpected : SignInPasswordError
    data object UnknownHost : SignInPasswordError
    data object Timeout : SignInPasswordError
}
