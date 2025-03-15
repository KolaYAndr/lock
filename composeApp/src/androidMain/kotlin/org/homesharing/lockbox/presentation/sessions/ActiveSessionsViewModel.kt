package org.homesharing.lockbox.presentation.sessions

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.sessions.Session
import org.homesharing.lockbox.domain.repository.AccountRepository

class ActiveSessionsViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<ActiveSessionsUiState> =
        MutableStateFlow(ActiveSessionsUiState())
    val uiState: StateFlow<ActiveSessionsUiState> get() = _uiState.asStateFlow()

    init {
        getSessions()
    }

    private fun getSessions() {
        viewModelScope.launch {
            val sessionsDeferred = async { accountRepository.getSessions() }
            val currentSessionIdDeferred = async { accountRepository.parseAccountInfo().sessionId }

            val s = sessionsDeferred.await()
            val id = currentSessionIdDeferred.await()

            if (s is Response.Success) _uiState.update {
                it.copy(sessions = s.data.filter { session -> session.sessionId != id }
                    .sortedBy { session -> session.lastAccessed }.reversed())
            }
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        getSessions()
    }

    fun finishOtherSessions() {

    }
}

@Immutable
data class ActiveSessionsUiState(
    val sessions: List<Session> = emptyList(),
    val isRefreshing: Boolean = false,
)
