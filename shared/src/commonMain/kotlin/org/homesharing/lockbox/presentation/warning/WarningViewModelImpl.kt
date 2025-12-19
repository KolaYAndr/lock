package org.homesharing.lockbox.presentation.warning

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class WarningViewModelImpl(
    private val accountRepository: AccountRepository,
) : WarningViewModel() {

    override fun logOut() {
        viewModelScope.launch {
            accountRepository.logOut()
        }
    }
}
