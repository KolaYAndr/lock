package org.homesharing.lockbox.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AuthRepository

internal class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun onPasswordChangeChange(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun singIn() {
        viewModelScope.launch {
            authRepository.login(
                email = uiState.value.email,
                password = uiState.value.password,
            )
        }
    }

    fun getCode() {
        viewModelScope.launch {
            authRepository.requestOtp(
                email = uiState.value.email,
            )
        }
    }
}

internal data class LoginUiState(
    val email: String,
    val password: String,
) {
    companion object {
        val Initial = LoginUiState(
            email = "",
            password = ""
        )
    }
}