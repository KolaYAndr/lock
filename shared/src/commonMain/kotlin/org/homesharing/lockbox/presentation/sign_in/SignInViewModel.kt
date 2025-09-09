package org.homesharing.lockbox.presentation.sign_in

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.homesharing.lockbox.presentation.login.EmailError

abstract class SignInViewModel : ViewModel() {
    abstract val uiState: StateFlow<SignInUiState>
    abstract fun onChangeEmail(newEmail: String)
    abstract fun onChangePassword(newPassword: String)
    abstract fun signIn()
}

data class SignInUiState(
    val email: String = "",
    val emailError: EmailError? = null,
    val password: String = "",
    val passwordError: SignInPasswordError? = null,
)

sealed interface SignInPasswordError {
    data object InvalidUserCredentials : SignInPasswordError
    data object Unexpected : SignInPasswordError
    data object UnknownHost : SignInPasswordError
    data object Timeout : SignInPasswordError
}
