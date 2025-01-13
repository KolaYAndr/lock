package org.homesharing.lockbox.presentation.security

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SecurityViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<SecurityUiState> =
        MutableStateFlow(SecurityUiState.ChangePassword())
    val uiState: StateFlow<SecurityUiState> get() = _uiState.asStateFlow()

    fun showDialog() {
        _uiState.update {
            SecurityUiState.ChangePassword(showDialog = true)
        }
    }

    fun hideDialog() {
        _uiState.update {
            SecurityUiState.ChangePassword(showDialog = false)
        }
    }

    fun onPasswordChanged() {
        _uiState.update {
            SecurityUiState.PasswordChanged
        }
    }
}

sealed class SecurityUiState {
    data class ChangePassword(val showDialog: Boolean = false) : SecurityUiState()
    data object PasswordChanged : SecurityUiState()
}

