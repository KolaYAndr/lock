package org.homesharing.lockbox.presentation.lockboxes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.BoxesRepository

class LockBoxesViewModel(
    private val accountRepository: AccountRepository,
    private val boxesRepository: BoxesRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<LockBoxesUiState> = MutableStateFlow(LockBoxesUiState())
    val uiState: StateFlow<LockBoxesUiState> get() = _uiState.asStateFlow()
    val boxes = boxesRepository.getBoxesPagingFlow().cachedIn(viewModelScope)

    init {
        fetchAccountInfo()
    }

    private fun fetchAccountInfo() {
        viewModelScope.launch {
            val info = accountRepository.parseAccountInfo()
            val imageLink = accountRepository.getAvatarLink(info.sub)
            _uiState.update { it.copy(avatarLink = imageLink) }
        }
    }

    fun onSearchChange(newSearchString: String) {
        _uiState.update { it.copy(searchString = newSearchString) }
    }
}

data class LockBoxesUiState(
    val searchString: String = "",
    val avatarLink: String = "",
)
