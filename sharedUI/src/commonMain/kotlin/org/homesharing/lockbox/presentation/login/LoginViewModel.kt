package org.homesharing.lockbox.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AuthRepository

internal class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, code = "", isCodeRequested = false, hasError = false) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, hasError = false) }
    }

    fun onCodeChange(code: String) {
        _uiState.update { it.copy(code = code.filter { digit -> digit in '0'..'9' }.take(6), hasError = false) }
    }

    fun signIn() {
        val state = uiState.value
        val email = state.email.trim()
        if (email.isBlank() || state.password.isBlank()) return
        submit { authRepository.login(email, state.password) }
    }

    fun requestCode() {
        val email = uiState.value.email.trim()
        if (email.isBlank()) return
        submit {
            authRepository.requestOtp(email).also { response ->
                if (response is Response.Success) {
                    _uiState.update { it.copy(code = "", isCodeRequested = true) }
                }
            }
        }
    }

    fun verifyCode() {
        val state = uiState.value
        if (!state.isCodeRequested || state.code.length != 6) return
        submit { authRepository.verifyOtp(state.email.trim(), state.code) }
    }

    private fun submit(request: suspend () -> Response<*>) {
        if (uiState.value.isSubmitting) return
        _uiState.update { it.copy(isSubmitting = true, hasError = false) }
        viewModelScope.launch {
            try {
                val response = request()
                _uiState.update { it.copy(hasError = response is Response.Failure) }
            } finally {
                _uiState.update { it.copy(isSubmitting = false) }
            }
        }
    }
}

internal data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val code: String = "",
    val isCodeRequested: Boolean = false,
    val isSubmitting: Boolean = false,
    val hasError: Boolean = false,
)
