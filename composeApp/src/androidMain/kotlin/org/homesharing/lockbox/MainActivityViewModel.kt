package org.homesharing.lockbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import org.homesharing.lockbox.domain.repository.AccountRepository

class MainActivityViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    fun canLogin() = viewModelScope.async {
        accountRepository.canLogin()
    }
}
