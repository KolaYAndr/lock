package org.homesharing.lockbox.presentation.lockboxes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotLoadAccountInfo
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotRefreshAccountInfo

class LockBoxesViewModel(
    private val accountRepository: AccountRepository,
    boxesRepository: BoxesRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<LockBoxesUiState> = MutableStateFlow(LockBoxesUiState())
    val uiState: StateFlow<LockBoxesUiState> get() = _uiState.asStateFlow()
    val boxes = boxesRepository.getBoxesPagingFlow().cachedIn(viewModelScope)

    init {
        fetchAccountInfo()
    }

    private fun fetchAccountInfo() {
        viewModelScope.launch {
            when (val info = accountRepository.parseAccountInfo()) {
                is Response.Success -> {
                    val imageLink = accountRepository.getImageLink(info.data.sub)
                    _uiState.update { it.copy(avatarLink = imageLink) }
                }

                is Response.Failure -> {
                    SnackbarController.receiveEvent(couldNotLoadAccountInfo { retryLoadingAccountInfo() })
                }
            }
        }
    }

    private suspend fun retryLoadingAccountInfo() {
        when (val info = accountRepository.refreshAccount()) {
            is Response.Success -> {
                val imageLink = accountRepository.getImageLink(info.data.sub)
                _uiState.update { it.copy(avatarLink = imageLink) }
            }

            is Response.Failure -> {
                SnackbarController.receiveEvent(couldNotRefreshAccountInfo { retryLoadingAccountInfo() })
            }
        }
    }
}

data class LockBoxesUiState(
    val avatarLink: String = "",
)
