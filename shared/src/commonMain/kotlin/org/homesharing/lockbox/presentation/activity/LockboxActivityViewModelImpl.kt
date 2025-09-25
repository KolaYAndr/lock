package org.homesharing.lockbox.presentation.activity

import org.homesharing.lockbox.domain.repository.AccountRepository

internal class LockboxActivityViewModelImpl(
    accountRepository: AccountRepository
) : LockboxActivityViewModel() {
    override val isLoggedIn = accountRepository.loggedIn()
}
