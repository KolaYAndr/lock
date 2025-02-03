package org.homesharing.lockbox.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

class LoginViewModel(private val repository: AccountRepository): ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState.asStateFlow()

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoggedIn = repository.canLogin()) }
        }
    }

    fun showCard() {
        _uiState.update {
            LoginUiState(showModal = true, loginType = LoginType.SignIn)
        }
    }

    fun toTypeAuth(authType: LoginType) {
        _uiState.update {
            it.copy(loginType = authType)
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
    val loginType: LoginType = LoginType.SignIn,
    val isLoggedIn: Boolean = false,
)
