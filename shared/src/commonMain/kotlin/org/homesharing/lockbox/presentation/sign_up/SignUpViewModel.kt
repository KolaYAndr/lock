package org.homesharing.lockbox.presentation.sign_up

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.homesharing.lockbox.presentation.login.EmailError

abstract class SignUpViewModel : ViewModel() {
    abstract val uiState: StateFlow<SignUpUiState>
    abstract fun onChangeEmail(newEmail: String)
    abstract fun onChangePassword(newPassword: String)
    abstract fun onChangePasswordRepeat(newRepeatedPassword: String)
    abstract fun sendEmailConfirmation()
    abstract suspend fun checkIfConfirmed()
    abstract fun signUp()
    abstract fun toStep(nextStep: SignUpStep)
}

data class SignUpUiState(
    val email: String = "",
    val emailError: EmailError? = null,
    val password: String = "",
    val passwordRepeated: String = "",
    val passwordRepeatedError: PasswordRepeatedError? = null,
    val signUpStep: SignUpStep = SignUpStep.EmailInput,
)

private const val STEP_ONE = 1
private const val STEP_TWO = 2
private const val STEP_THREE = 3

sealed class SignUpStep(val index: Int) {
    data object EmailInput : SignUpStep(STEP_ONE)
    data object PasswordInput : SignUpStep(STEP_TWO)
    data object EmailConfirmation : SignUpStep(STEP_THREE)
}

sealed interface PasswordRepeatedError {
    data object PasswordsDontMatch : PasswordRepeatedError
    data object UserExists : PasswordRepeatedError
    data object Unexpected : PasswordRepeatedError
}
