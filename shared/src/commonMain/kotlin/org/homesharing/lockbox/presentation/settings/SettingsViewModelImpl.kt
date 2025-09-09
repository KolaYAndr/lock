package org.homesharing.lockbox.presentation.settings

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotLoadAccountInfo
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotRefreshAccountInfo

class SettingsViewModelImpl(
    private val repository: AccountRepository
) : SettingsViewModel() {
    override val uiState = MutableStateFlow(SettingsUiState())

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            when (val info = repository.parseAccountInfo()) {
                is Response.Success -> {
                    launch {
                        uiState.update {
                            it.copy(
                                fullName = info.data.fullName ?: "",
                                email = info.data.email,
                                sub = info.data.sub
                            )
                        }
                    }

                    launch {
                        uiState.update { it.copy(imageLink = repository.getImageLink(info.data.sub)) }
                    }
                }

                is Response.Failure -> SnackbarController.receiveEvent(couldNotLoadAccountInfo { fetchInfo() })
            }
        }
    }

    override fun refreshAccountInfo() {
        viewModelScope.launch {
            uiState.update { it.copy(isRefreshing = true) }
            when (val info = repository.refreshAccount()) {
                is Response.Success -> {
                    launch {
                        uiState.update {
                            it.copy(
                                fullName = info.data.fullName ?: "",
                                email = info.data.email,
                            )
                        }
                    }
                }

                is Response.Failure -> SnackbarController.receiveEvent(couldNotRefreshAccountInfo { refreshAccountInfo() })
            }
            uiState.update { it.copy(isRefreshing = false) }
        }
    }

    override fun logOut() {
        viewModelScope.launch {
            repository.logOut()
        }
    }
}