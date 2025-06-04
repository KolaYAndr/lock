package org.homesharing.lockbox.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class LoginViewModel(private val repository: AccountRepository): ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState.asStateFlow()

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        viewModelScope.launch {
            repository.loggedIn().collectLatest { hasToken ->
                _uiState.update { it.copy(isLoggedIn = hasToken) }
            }
        }
    }

    fun toTypeAuth(authType: LoginType) {
        _uiState.update {
            it.copy(loginType = authType)
        }
    }
}


private const val STEP_ONE = 1
private const val STEP_TWO = 2

internal sealed class LoginType(val hierarchyIndex: Int) {
    data object SignIn : LoginType(STEP_ONE)
    data object SignUp : LoginType(STEP_TWO)
}

internal data class LoginUiState(
    val loginType: LoginType = LoginType.SignIn,
    val isLoggedIn: Boolean = false,
)
