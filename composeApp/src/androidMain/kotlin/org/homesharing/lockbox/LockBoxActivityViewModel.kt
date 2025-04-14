package org.homesharing.lockbox

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.repository.AccountRepository

class LockBoxActivityViewModel(accountRepository: AccountRepository) : ViewModel() {
    val isLoggedIn: Flow<Boolean> = accountRepository.loggedIn()
}

