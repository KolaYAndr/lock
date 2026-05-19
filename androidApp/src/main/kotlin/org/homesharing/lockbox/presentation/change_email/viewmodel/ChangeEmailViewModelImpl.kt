//package org.homesharing.lockbox.presentation.change_email.viewmodel
//
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.homesharing.lockbox.domain.api.response.Response
//import org.homesharing.lockbox.domain.repository.AuthRepository
//import org.homesharing.lockbox.presentation.snackbar.SnackbarController
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotSendLinkToChangeEmail
//
//internal class ChangeEmailViewModelImpl(
//    private val repository: AuthRepository,
//) : ChangeEmailViewModel() {
//    override val uiState = MutableStateFlow(ChangeEmailUiState())
//    private val emailPattern =
//        Regex.fromLiteral("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
//
//    override fun toStep(step: ChangeEmailStep) = uiState.update {
//        it.copy(changeEmailStep = step)
//    }
//
//    override fun onEmailChange(newEmail: String) = uiState.update {
//        it.copy(newEmail = newEmail, emailError = false)
//    }
//
//    override fun changeEmail() {
//        if (emailPattern.matches(uiState.value.newEmail)) {
//            viewModelScope.launch {
//                when (repository.sendConfirmation(uiState.value.newEmail)) {
//                    is Response.Failure -> SnackbarController.receiveEvent(
//                        couldNotSendLinkToChangeEmail { changeEmail() })
//
//                    is Response.Success -> toStep(ChangeEmailStep.ConfirmNewEmail)
//                }
//            }
//        } else uiState.update { it.copy(emailError = true) }
//    }
//}
