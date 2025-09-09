package org.homesharing.lockbox.presentation.sessions

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.CommonStringKeys
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

class ActiveSessionsViewModelImpl(
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

            val sessions = sessionsDeferred.await()
            val id = currentSessionIdDeferred.await()

            when {
                sessions is Response.Success && id is Response.Success -> uiState.update {
                    it.copy(
                        sessions = sessions.data.sessions
                            .filter { session -> session.sessionId != id.data.sessionId }
                            .sortedBy { session -> session.lastAccessed }
                            .reversed())

                }

                else -> SnackbarController.receiveEvent(couldNotLoadActiveSessions { refreshSessions() })
            }

            uiState.update { it.copy(isRefreshing = false) }
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

    private companion object {
        fun couldNotLoadActiveSessions(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotLoadActiveSessions,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = { onRetry() }
            )
        )

        fun couldNotCloseOtherSessions(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotCloseOtherSessions,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = { onRetry() }
            )
        )
    }
}
