package org.homesharing.lockbox.presentation.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> get() = _uiState.asStateFlow()

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
        hideDialog()
    }
}

//TODO change defaults
data class SettingsUiState(
    val name: String = "Иван",
    val surname: String = "Иванов",
    val email: String = "ivan.ivanov@email.com",
    val showDialog: Boolean = false
)