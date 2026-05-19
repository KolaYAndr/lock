//package org.homesharing.lockbox.presentation.sessions.viewmodel
//
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.homesharing.lockbox.domain.api.response.Response
//import org.homesharing.lockbox.domain.repository.AccountRepository
//import org.homesharing.lockbox.presentation.snackbar.SnackbarController
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotCloseOtherSessions
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotLoadActiveSessions
//
//internal class ActiveSessionsViewModelImpl(
//    private val accountRepository: AccountRepository,
//) : ActiveSessionsViewModel() {
//    override val uiState = MutableStateFlow(ActiveSessionsUiState())
//
//    init {
//        getSessions()
//    }
//
//    private fun getSessions() {
//        viewModelScope.launch {
//            when (val sessions = accountRepository.getSessions()) {
//                is Response.Success -> uiState.update {
//                    it.copy(
//                        sessions = sessions.data.sessions
//                            .sortedByDescending { session -> session.lastActive }
//                    )
//                }
//
//                is Response.Failure -> SnackbarController.receiveEvent(couldNotLoadActiveSessions { refreshSessions() })
//            }
//
//            uiState.update { it.copy(isRefreshing = false) }
//        }
//    }
//
//    override fun refreshSessions() {
//        uiState.update { it.copy(isRefreshing = true) }
//        getSessions()
//    }
//
//    override fun finishOtherSessions() {
//        viewModelScope.launch {
//            when (accountRepository.closeOtherSessions()) {
//                is Response.Failure -> SnackbarController.receiveEvent(couldNotCloseOtherSessions { finishOtherSessions() })
//
//                is Response.Success -> {
//                    getSessions()
//                }
//            }
//        }
//    }
//}
