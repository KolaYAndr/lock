package org.homesharing.lockbox.presentation.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

class SecurityViewModel(
    private val accountRepository: AccountRepository,
) : ViewModel() {
//    private val _uiState: MutableStateFlow<SecurityUiState> =
//        MutableStateFlow(SecurityUiState.OptionsScreen)
//    val uiState: StateFlow<SecurityUiState> get() = _uiState.asStateFlow()
//
//    fun onPasswordChanged() {
//        _uiState.update {
//            SecurityUiState.PasswordChanged
//        }
//    }
//
//    fun backToChangePassword() {
//        _uiState.update {
//            SecurityUiState.OptionsScreen
//        }
//    }

    fun sendLinkToChangePassword() {
        viewModelScope.launch {
            when (accountRepository.sendLinkToChangeEmail()) {
                is Response.Failure -> SnackbarController.receiveEvent(
                    couldntSendLinkToEmail { sendLinkToChangePassword() }
                )

                is Response.Success -> SnackbarController.receiveEvent(
                    linkSentToResetPassword
                )
            }
        }
    }

    private companion object {
        fun couldntSendLinkToEmail(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.couldnt_send_link_to_change_password,
            action = SnackbarAction(
                name = R.string.retry,
                effect = { onRetry() }
            )
        )

        val linkSentToResetPassword = SnackbarEvent(
            message = R.string.link_sent_to_change_password
        )
    }
}
//
//sealed class SecurityUiState {
//    data object OptionsScreen : SecurityUiState()
//    data object PasswordChanged : SecurityUiState()
//}

