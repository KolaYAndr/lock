package org.homesharing.lockbox.presentation.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class SettingsViewModel : ViewModel() {
    abstract val uiState: StateFlow<SettingsUiState>
    abstract fun refreshAccountInfo()
    abstract fun logOut()
}

data class SettingsUiState(
    val fullName: String = "",
    val email: String = "",
    val imageLink: String = "",
    val sub: String = "",
    val isRefreshing: Boolean = false,
)
