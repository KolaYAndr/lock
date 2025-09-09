package org.homesharing.lockbox.presentation.security

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.CommonStringKeys
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDuration
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

class SecurityViewModelImpl(
    private val accountRepository: AccountRepository,
) : SecurityViewModel() {
    override fun sendLinkToChangePassword() {
        viewModelScope.launch {
            when (accountRepository.sendLinkToChangeEmail()) {
                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotSendLinkToEmail { sendLinkToChangePassword() })

                is Response.Success -> SnackbarController.receiveEvent(linkSentToResetPassword)
            }
        }
    }

    private companion object {
        fun couldNotSendLinkToEmail(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotSendLinkToChangePassword,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = { onRetry() }
            )
        )

        val linkSentToResetPassword = SnackbarEvent(
            messageKey = CommonStringKeys.LinkSentToChangePassword,
            duration = SnackbarDuration.Short
        )
    }
}
