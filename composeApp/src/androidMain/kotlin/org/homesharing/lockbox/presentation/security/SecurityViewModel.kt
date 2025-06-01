package org.homesharing.lockbox.presentation.security

import androidx.compose.material3.SnackbarDuration
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

    fun sendLinkToChangePassword() {
        viewModelScope.launch {
            when (accountRepository.sendLinkToChangeEmail()) {
                is Response.Failure -> SnackbarController.receiveEvent(
                    couldNotSendLinkToEmail { sendLinkToChangePassword() }
                )

                is Response.Success -> SnackbarController.receiveEvent(
                    linkSentToResetPassword
                )
            }
        }
    }

    private companion object {
        fun couldNotSendLinkToEmail(onRetry: () -> Unit) = SnackbarEvent(
            message = R.string.could_not_send_link_to_change_password,
            action = SnackbarAction(
                name = R.string.retry,
                effect = { onRetry() }
            )
        )

        val linkSentToResetPassword = SnackbarEvent(
            message = R.string.link_sent_to_change_password,
            duration = SnackbarDuration.Short
        )
    }
}

