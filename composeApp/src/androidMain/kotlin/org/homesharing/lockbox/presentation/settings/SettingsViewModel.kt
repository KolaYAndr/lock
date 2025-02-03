package org.homesharing.lockbox.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

class SettingsViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> get() = _uiState.asStateFlow()

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            val info = repository.parseAccountInfo()
            _uiState.update {
                it.copy(
                    fullName = info.name,
                    email = info.email
                )
            }
        }
    }

    fun showDialog() {
        _uiState.update {
            it.copy(showDialog = true)
        }
    }

    fun hideDialog() {
        _uiState.update {
            it.copy(showDialog = false)
        }
    }

    fun logOut() {
        viewModelScope.launch {
            repository.logOut()
            hideDialog()
        }
    }
}

data class SettingsUiState(
    val fullName: String = "",
    val email: String = "",
    val showDialog: Boolean = false,
)