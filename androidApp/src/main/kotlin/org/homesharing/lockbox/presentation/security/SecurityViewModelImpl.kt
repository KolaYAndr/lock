package org.homesharing.lockbox.presentation.security

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar.SnackbarController
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotSendLinkToChangePassword
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.linkSentToResetPassword

internal class SecurityViewModelImpl(
    private val accountRepository: AccountRepository,
) : SecurityViewModel() {
    override fun sendLinkToChangePassword() {
        viewModelScope.launch {
            when (accountRepository.sendLinkToChangeEmail()) {
                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotSendLinkToChangePassword { sendLinkToChangePassword() })

                is Response.Success -> SnackbarController.receiveEvent(linkSentToResetPassword)
            }
        }
    }
}
