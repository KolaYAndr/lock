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
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.sessions.Session
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

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
            val currentSessionIdDeferred = async { accountRepository.parseAccountInfo() }

            val s = sessionsDeferred.await()
            val id = currentSessionIdDeferred.await()

            when {
                s is Response.Success && id is Response.Success -> {
                    _uiState.update {
                        it.copy(sessions = s.data.filter { session -> session.sessionId != id.data.sessionId }
                            .sortedBy { session -> session.lastAccessed }.reversed())
                    }
                }

                else -> {
                    SnackbarController.receiveEvent(couldNotLoadActiveSessions { refreshSessions() })
                }
            }

            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    fun refreshSessions() {
        _uiState.update { it.copy(isRefreshing = true) }
        getSessions()
    }

    fun finishOtherSessions() {
        //TODO
    }

    private companion object {
        fun couldNotLoadActiveSessions(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_load_active_sessions,
            action = SnackbarAction(
                name = R.string.retry,
                effect = { onRetry() }
            )
        )
    }
}

@Immutable
data class ActiveSessionsUiState(
    val sessions: List<Session> = emptyList(),
    val isRefreshing: Boolean = false,
)
