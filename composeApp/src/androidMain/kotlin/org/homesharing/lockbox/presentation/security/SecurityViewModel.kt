package org.homesharing.lockbox.presentation.security

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SecurityViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<SecurityUiState> =
        MutableStateFlow(SecurityUiState.OptionsScreen)
    val uiState: StateFlow<SecurityUiState> get() = _uiState.asStateFlow()

    fun onPasswordChanged() {
        _uiState.update {
            SecurityUiState.PasswordChanged
        }
    }

    fun backToChangePassword() {
        _uiState.update {
            SecurityUiState.OptionsScreen
        }
    }
}

sealed class SecurityUiState {
    data object OptionsScreen : SecurityUiState()
    data object PasswordChanged : SecurityUiState()
}

