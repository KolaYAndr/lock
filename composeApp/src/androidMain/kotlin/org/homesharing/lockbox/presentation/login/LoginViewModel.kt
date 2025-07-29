package org.homesharing.lockbox.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class LoginViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _loginType: MutableStateFlow<LoginType> = MutableStateFlow(LoginType.SignIn)
    val loginType: StateFlow<LoginType> = _loginType.asStateFlow()

    private val _loggedIn: MutableSharedFlow<Unit> = MutableSharedFlow()
    val loggedIn: SharedFlow<Unit> = _loggedIn.asSharedFlow()

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() = viewModelScope.launch {
        repository.loggedIn().collectLatest { hasToken ->
            if (hasToken) _loggedIn.emit(Unit)
        }
    }

    fun toTypeAuth(authType: LoginType) = _loginType.update { authType }
}

private const val STEP_ONE = 1
private const val STEP_TWO = 2

internal sealed class LoginType(val hierarchyIndex: Int) {
    data object SignIn : LoginType(STEP_ONE)
    data object SignUp : LoginType(STEP_TWO)
}

