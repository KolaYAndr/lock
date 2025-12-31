package org.homesharing.lockbox

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.homesharing.lockbox.domain.repository.AccountRepository

private const val SUBSCRIPTION_THRESHOLD = 5000L

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
        started = SharingStarted.WhileSubscribed(SUBSCRIPTION_THRESHOLD),
        initialValue = LoginState.Loading
    )
}
