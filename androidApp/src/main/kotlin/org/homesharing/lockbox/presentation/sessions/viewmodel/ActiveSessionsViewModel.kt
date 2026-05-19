//package org.homesharing.lockbox.presentation.sessions.viewmodel
//
//import androidx.compose.runtime.Immutable
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.StateFlow
//import org.homesharing.lockbox.domain.model.sessions.Session
//
//internal abstract class ActiveSessionsViewModel : ViewModel() {
//    abstract val uiState: StateFlow<ActiveSessionsUiState>
//    abstract fun refreshSessions()
//    abstract fun finishOtherSessions()
//}
//
//@Immutable
//internal data class ActiveSessionsUiState(
//    val sessions: List<Session> = emptyList(),
//    val isRefreshing: Boolean = false,
//)
