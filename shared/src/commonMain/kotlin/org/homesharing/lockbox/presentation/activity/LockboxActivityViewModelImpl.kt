package org.homesharing.lockbox.presentation.activity

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class LockboxActivityViewModelImpl(
    accountRepository: AccountRepository,
) : LockboxActivityViewModel() {

    override val loginState = accountRepository.loggedIn().map { loggedIn ->
        if (loggedIn) {
            LoginState.LoggedIn
        } else {
            LoginState.NotLoggedIn
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LoginState.Loading
    )
}
