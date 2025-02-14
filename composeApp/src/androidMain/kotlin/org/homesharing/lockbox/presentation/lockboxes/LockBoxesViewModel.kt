package org.homesharing.lockbox.presentation.lockboxes

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.BoxesRepository

private const val PAGE_SIZE = 10

class LockBoxesViewModel(
    private val accountRepository: AccountRepository,
    private val boxesRepository: BoxesRepository,
    private val boxesPagingSource: BoxesPagingSource,
) : ViewModel() {

    private val _uiState: MutableStateFlow<LockBoxesUiState> = MutableStateFlow(LockBoxesUiState())
    val uiState: StateFlow<LockBoxesUiState> get() = _uiState.asStateFlow()
    val boxes = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = PAGE_SIZE
        ),
        pagingSourceFactory = { boxesPagingSource }
    ).flow.flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    init {
        fetchAccountInfo()
    }

    private fun fetchAccountInfo() {
        viewModelScope.launch {
            val info = accountRepository.parseAccountInfo()
            _uiState.update {
                it.copy(
                    sub = info.sub
                )
            }
            val imageLink = accountRepository.getAvatarLink(info.sub)
            _uiState.update { it.copy(avatarLink = imageLink) }
        }
    }

    fun showModal() {
        _uiState.update {
            it.copy(showModal = true)
        }
    }

    fun hideModal() {
        _uiState.update {
            it.copy(showModal = false)
        }
    }

    fun onSearchChange(newSearchString: String) {
        _uiState.update { it.copy(searchString = newSearchString) }
    }
}

@Immutable
data class LockBoxesUiState(
    val showModal: Boolean = false,
    val searchString: String = "",
    val sub: String = "",
    val avatarLink: String = "",
)
