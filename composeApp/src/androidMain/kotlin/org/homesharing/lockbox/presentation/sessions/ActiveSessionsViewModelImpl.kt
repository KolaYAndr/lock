package org.homesharing.lockbox.presentation.sessions

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.account_info.AccountInfo
import org.homesharing.lockbox.domain.model.sessions.Sessions
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar.SnackbarController
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotCloseOtherSessions
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotLoadActiveSessions

internal class ActiveSessionsViewModelImpl(
    private val accountRepository: AccountRepository,
) : ActiveSessionsViewModel() {
    override val uiState = MutableStateFlow(ActiveSessionsUiState())

    init {
        getSessions()
    }

    private fun getSessions() {
        viewModelScope.launch {
            val sessionsDeferred = async { accountRepository.getSessions() }
            val currentSessionIdDeferred = async { accountRepository.parseAccountInfo() }

            ActiveSessionsUiStateMapper().map(
                sessions = sessionsDeferred.await(),
                id = currentSessionIdDeferred.await()
            )
        }
    }

    override fun refreshSessions() {
        uiState.update { it.copy(isRefreshing = true) }
        getSessions()
    }

    override fun finishOtherSessions() {
        viewModelScope.launch {
            when (val accountInfo = accountRepository.parseAccountInfo()) {
                is Response.Failure -> SnackbarController.receiveEvent(couldNotCloseOtherSessions { finishOtherSessions() })

                is Response.Success -> {
                    accountRepository.closeOtherSessions(accountInfo.data.sessionId)
                    getSessions()
                }
            }
        }
    }

    inner class ActiveSessionsUiStateMapper {
        suspend fun map(sessions: Response<Sessions>, id: Response<AccountInfo>) {
            when {
                sessions is Response.Success && id is Response.Success -> uiState.update {
                    it.copy(
                        sessions = sessions.data.sessions
                            .filter { session -> session.sessionId != id.data.sessionId }
                            .sortedByDescending { session -> session.lastAccessed }
                    )
                }

                else -> SnackbarController.receiveEvent(couldNotLoadActiveSessions { refreshSessions() })
            }

            uiState.update { it.copy(isRefreshing = false) }
        }
    }
}
