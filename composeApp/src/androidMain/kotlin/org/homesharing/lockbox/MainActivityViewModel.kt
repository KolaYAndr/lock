package org.homesharing.lockbox

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.repository.AccountRepository

class MainActivityViewModel(accountRepository: AccountRepository) : ViewModel() {
    val isLoggedIn: Flow<Boolean> = accountRepository.loggedIn()
}

