package org.homesharing.lockbox.presentation.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

class LoginViewModelImpl(
    private val repository: AccountRepository
) : LoginViewModel() {
    override val loginType: MutableStateFlow<LoginType> = MutableStateFlow(LoginType.SignIn)
    override val loggedIn: MutableSharedFlow<Unit> = MutableSharedFlow()

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() = viewModelScope.launch {
        repository.loggedIn().collectLatest { hasToken ->
            if (hasToken) loggedIn.emit(Unit)
        }
    }

    override fun toTypeAuth(authType: LoginType) = loginType.update { authType }
}
