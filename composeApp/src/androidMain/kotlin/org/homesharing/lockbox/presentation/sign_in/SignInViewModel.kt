package org.homesharing.lockbox.presentation.sign_in

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AuthRepository

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> get() = _uiState.asStateFlow()
    private val emailPattern = Patterns.EMAIL_ADDRESS

    fun editEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail, emailErrorMessage = null)
        }
    }

    fun editPassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword, passwordErrorMessage = null)
        }
    }

    fun showPasswordToggle(newValue: Boolean) {
        _uiState.update {
            it.copy(isPasswordSeen = newValue)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            try {
                if (!emailPattern.matcher(uiState.value.email).matches()) {
                    _uiState.update { it.copy(emailErrorMessage = "Doesn't match email pattern") }
                } else {
                    val response = authRepository.loadTokensInitially(
                        uiState.value.email,
                        uiState.value.password
                    )

                    if (!response.error.isNullOrEmpty()) {
                        _uiState.update { state ->
                            state.copy(
                                passwordErrorMessage = response.errorDescription,
                                emailErrorMessage = response.errorDescription
                            )
                        }

                    } else {
                        _uiState.update { state -> state.copy(loggedIn = true) }
                    }

                }
            } catch (e: Exception) {
                _uiState.update { it.copy(emailErrorMessage = e.message) }
            }
        }
    }
}

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordSeen: Boolean = false,
    val passwordErrorMessage: String? = null,
    val emailErrorMessage: String? = null,
    val loggedIn: Boolean = false,
)
