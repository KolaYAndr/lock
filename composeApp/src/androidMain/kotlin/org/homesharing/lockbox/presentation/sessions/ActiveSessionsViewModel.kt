package org.homesharing.lockbox.presentation.sessions

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.model.Session
import org.homesharing.lockbox.domain.repository.AccountRepository

class ActiveSessionsViewModel(private val accountRepository: AccountRepository): ViewModel() {
    private val _uiState: MutableStateFlow<ActiveSessionsUiState> = MutableStateFlow(ActiveSessionsUiState())
    val uiState: StateFlow<ActiveSessionsUiState> get() = _uiState.asStateFlow()

    init {
        getSessions()
    }

    private fun getSessions() {
        viewModelScope.launch {
            val sessions = accountRepository.getSessions()
            _uiState.update { it.copy(sessions = sessions) }
        }
    }

    fun finishOtherSessions() {

    }
}

@Immutable
data class ActiveSessionsUiState(
    val sessions: List<Session> = emptyList()
)
