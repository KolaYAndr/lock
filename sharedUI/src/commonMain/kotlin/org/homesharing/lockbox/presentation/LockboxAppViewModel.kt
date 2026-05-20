package org.homesharing.lockbox.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.homesharing.lockbox.domain.repository.AccountRepository

private const val SUBSCRIPTION_THRESHOLD = 5000L

class LockboxAppViewModel(
    accountRepository: AccountRepository,
) : ViewModel() {
    val loginState = accountRepository.loggedIn.map { loggedIn ->
        if (loggedIn) {
            LoginState.LoggedIn
        } else {
            LoginState.NotLoggedIn
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SUBSCRIPTION_THRESHOLD),
        initialValue = LoginState.Loading
    )
}

sealed interface LoginState {
    object Loading : LoginState
    object LoggedIn : LoginState
    object NotLoggedIn : LoginState
}
