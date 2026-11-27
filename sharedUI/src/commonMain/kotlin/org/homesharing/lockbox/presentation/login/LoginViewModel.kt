package org.homesharing.lockbox.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AuthRepository

internal class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events.asSharedFlow()

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
            val email = uiState.value.email
            val response = authRepository.requestOtp(
                email = email,
            )
            if (response is Response.Success) {
                _events.emit(
                    LoginEvent.NavigateToLoginOtp(
                        email = email,
                        resendAfter = response.data.resendAfter,
                        expiresIn = response.data.expiresIn,
                    )
                )
            }
        }
    }
}

internal sealed interface LoginEvent {
    data class NavigateToLoginOtp(
        val email: String,
        val resendAfter: Int,
        val expiresIn: Int,
    ) : LoginEvent
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