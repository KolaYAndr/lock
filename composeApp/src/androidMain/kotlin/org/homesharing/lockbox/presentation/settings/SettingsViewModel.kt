package org.homesharing.lockbox.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotLoadAccountInfo
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotRefreshAccountInfo

internal class SettingsViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            when (val info = repository.parseAccountInfo()) {
                is Response.Success -> {
                    launch {
                        _uiState.update {
                            it.copy(
                                fullName = info.data.fullName ?: "",
                                email = info.data.email,
                                sub = info.data.sub
                            )
                        }
                    }

                    launch {
                        val imageLink = repository.getImageLink(info.data.sub)
                        _uiState.update { it.copy(imageLink = imageLink) }
                    }
                }

                is Response.Failure -> SnackbarController.receiveEvent(couldNotLoadAccountInfo { fetchInfo() })
            }
        }
    }

    fun refreshAccountInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            when (val info = repository.refreshAccount()) {
                is Response.Success -> {
                    launch {
                        _uiState.update {
                            it.copy(
                                fullName = info.data.fullName ?: "",
                                email = info.data.email,
                            )
                        }
                    }
                }

                is Response.Failure -> SnackbarController.receiveEvent(couldNotRefreshAccountInfo { refreshAccountInfo() })
            }
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            repository.logOut()
        }
    }
}

internal data class SettingsUiState(
    val fullName: String = "",
    val email: String = "",
    val imageLink: String = "",
    val sub: String = "",
    val isRefreshing: Boolean = false,
)
