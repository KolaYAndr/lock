package org.homesharing.lockbox.presentation.sign_in.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

internal abstract class SignInViewModel : ViewModel() {
    abstract val uiState: StateFlow<SignInUiState>
    abstract fun onChangeEmail(newEmail: String)
    abstract fun onChangePassword(newPassword: String)
    abstract fun signIn()
}

internal data class SignInUiState(
    val email: String = "",
    val emailError: Boolean = false,
    val password: String = "",
    val passwordError: SignInPasswordError? = null,
)

internal sealed interface SignInPasswordError {
    data object InvalidUserCredentials : SignInPasswordError
    data object Unexpected : SignInPasswordError
    data object UnknownHost : SignInPasswordError
    data object Timeout : SignInPasswordError
}
