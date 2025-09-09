package org.homesharing.lockbox.presentation.sessions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.homesharing.lockbox.domain.model.sessions.Session
import org.homesharing.lockbox.presentation.immutable.Immutable

abstract class ActiveSessionsViewModel : ViewModel() {
    abstract val uiState: StateFlow<ActiveSessionsUiState>
    abstract fun refreshSessions()
    abstract fun finishOtherSessions()
}

@Immutable
data class ActiveSessionsUiState(
    val sessions: List<Session> = emptyList(),
    val isRefreshing: Boolean = false,
)
