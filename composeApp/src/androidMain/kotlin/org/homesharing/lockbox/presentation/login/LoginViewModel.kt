package org.homesharing.lockbox.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState.asStateFlow()

    fun showCard() {
        _uiState.update {
            LoginUiState(showModal = true, loginType = LoginType.SignIn)
        }
    }

    fun signIn() {
        _uiState.update {
            LoginUiState(showModal = true, loginType = LoginType.SignIn)
        }
    }

    fun signUp() {
        _uiState.update {
            LoginUiState(showModal = true, loginType = LoginType.SignUp)
        }
    }

    fun forgotPassword() {
        _uiState.update {
            LoginUiState(showModal = true, loginType = LoginType.ForgotPassword)
        }
    }

    fun hideCard() {
        _uiState.update {
            LoginUiState(showModal = false)
        }
    }
}

sealed class LoginType {
    data object SignUp : LoginType()
    data object SignIn : LoginType()
    data object ForgotPassword : LoginType()
}

data class LoginUiState(
    val showModal: Boolean = false,
    val loginType: LoginType = LoginType.SignIn
)