package org.homesharing.lockbox.presentation.settings.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.account_info.AccountInfo
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar.SnackbarController
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotLoadAccountInfo
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotRefreshAccountInfo

internal class SettingsViewModelImpl(
    private val repository: AccountRepository,
) : SettingsViewModel() {
    override val uiState = MutableStateFlow(SettingsUiState())

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            SettingsUiStataMapper().map(repository.parseAccountInfo())
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

    inner class SettingsUiStataMapper {
        suspend fun map(from: Response<AccountInfo>) {
            when (from) {
                is Response.Success -> {
                    uiState.update {
                        it.copy(
                            fullName = from.data.fullName.orEmpty(),
                            email = from.data.email,
                            sub = from.data.sub,
                            imageLink = repository.getImageLink(from.data.sub)
                        )
                    }
                }

                is Response.Failure -> SnackbarController.receiveEvent(couldNotLoadAccountInfo { fetchInfo() })

            }

        }
    }
}
