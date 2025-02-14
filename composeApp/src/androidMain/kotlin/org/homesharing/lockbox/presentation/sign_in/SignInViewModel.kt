package org.homesharing.lockbox.presentation.sign_in

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.data.network.model.InitialTokenLoadResponse
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
                if (emailPattern.matcher(uiState.value.email).matches()) {
                    authRepository.loadTokensInitially(
                        uiState.value.email,
                        uiState.value.password
                    ).also { response ->
                        when (response) {
                            is InitialTokenLoadResponse.InitialTokenLoadFailResponse -> {
                                _uiState.update { state ->
                                    state.copy(
                                        passwordErrorMessage = response.errorDescription,
                                        emailErrorMessage = response.errorDescription
                                    )
                                }
                            }

                            is InitialTokenLoadResponse.InitialTokenLoadSuccessResponse -> {
                                _uiState.update { state -> state.copy(loggedIn = true) }
                            }
                        }
                    }
                } else _uiState.update { it.copy(emailErrorMessage = "Doesn't match email pattern") }

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
