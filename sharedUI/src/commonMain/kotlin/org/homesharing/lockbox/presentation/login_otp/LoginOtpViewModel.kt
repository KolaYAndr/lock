package org.homesharing.lockbox.presentation.login_otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AuthRepository
import kotlin.time.Duration.Companion.seconds

private const val OTP_LENGTH = 6

internal class LoginOtpViewModel(
    private val email: String,
    resendAfter: Int,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LoginOtpUiState(
            email = email,
            code = "",
            isResendAvailable = resendAfter <= 0,
            isLoading = false,
            isError = false,
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginOtpEvent>()
    val events: SharedFlow<LoginOtpEvent> = _events.asSharedFlow()

    private var timerJob: Job? = null

    init {
        startResendTimer(resendAfter)
    }

    fun onIntent(intent: LoginOtpIntent) {
        when (intent) {
            is LoginOtpIntent.CodeChanged -> onCodeChanged(intent.value)
            LoginOtpIntent.Submit -> submitCode()
            LoginOtpIntent.ResendCode -> resendCode()
        }
    }

    private fun onCodeChanged(newCode: String) {
        val filteredCode = newCode.filter(Char::isDigit).take(OTP_LENGTH)
        _uiState.update {
            it.copy(
                code = filteredCode,
                isError = false,
            )
        }
    }

    private fun submitCode() {
        if (_uiState.value.code.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }
            when (val response = authRepository.verifyOtp(email = email, code = _uiState.value.code)) {
                is Response.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(LoginOtpEvent.Verified(isNewUser = response.data.isNewUser))
                }

                is Response.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                        )
                    }
                }
            }
        }
    }

    private fun resendCode() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }
            when (val response = authRepository.requestOtp(email = email)) {
                is Response.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    startResendTimer(response.data.resendAfter)
                }

                is Response.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                        )
                    }
                }
            }
        }
    }

    private fun startResendTimer(seconds: Int) {
        timerJob?.cancel()
        val delaySeconds = seconds.coerceAtLeast(0)
        if (delaySeconds == 0) {
            _uiState.update { it.copy(isResendAvailable = true) }
            return
        }

        _uiState.update { it.copy(isResendAvailable = false) }
        timerJob = viewModelScope.launch {
            delay(delaySeconds.seconds)
            _uiState.update { it.copy(isResendAvailable = true) }
        }
    }
}

internal data class LoginOtpUiState(
    val email: String,
    val code: String,
    val isResendAvailable: Boolean,
    val isLoading: Boolean,
    val isError: Boolean,
)

internal sealed interface LoginOtpIntent {
    data class CodeChanged(val value: String) : LoginOtpIntent
    data object Submit : LoginOtpIntent
    data object ResendCode : LoginOtpIntent
}

internal sealed interface LoginOtpEvent {
    data class Verified(val isNewUser: Boolean) : LoginOtpEvent
}
