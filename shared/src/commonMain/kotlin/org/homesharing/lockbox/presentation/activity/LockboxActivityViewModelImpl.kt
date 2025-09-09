package org.homesharing.lockbox.presentation.activity

import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.repository.AccountRepository

class LockboxActivityViewModelImpl(accountRepository: AccountRepository) : LockboxActivityViewModel() {
    override val isLoggedIn: Flow<Boolean> = accountRepository.loggedIn()
}
