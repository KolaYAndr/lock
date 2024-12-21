package org.homesharing.lockbox.presentation.sign_in

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> get() = _uiState.asStateFlow()

    fun editEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun editPassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun showPasswordToggle(newValue: Boolean) {
        _uiState.update {
            it.copy(isPasswordSeen = newValue)
        }
    }
}

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordSeen: Boolean = false,
    val passwordErrorMessage: String? = null,
    val emailErrorMessage: String? = null
)