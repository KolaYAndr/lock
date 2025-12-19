package org.homesharing.lockbox.presentation.account

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.CommonStringKeys
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDuration
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

private const val UPDATE_TIME = 500L

internal class AccountViewModelImpl(
    private val repository: AccountRepository
) : AccountViewModel() {
    override val uiState = MutableStateFlow(AccountUiState())

    init {
        fetchInfo()
        sendUpdates()
    }

    @OptIn(FlowPreview::class)
    private fun sendUpdates() {
        viewModelScope.launch {
            uiState.map { it.name to it.surname }
                .distinctUntilChanged()
                .debounce(UPDATE_TIME)
                .collectLatest { (name, surname) ->
                    val response = repository.updateInfo(name = name, surname = surname)

                    if (response is Response.Failure)
                        SnackbarController.receiveEvent(couldNotUpdateUserInfoEvent { retryUpdatingInfo() })
                }
        }
    }

    private suspend fun retryUpdatingInfo() {
        val response = repository.updateInfo(
            name = uiState.value.name,
            surname = uiState.value.surname
        )

        if (response is Response.Failure)
            SnackbarController.receiveEvent(couldNotUpdateUserInfoEvent { retryUpdatingInfo() })
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            when (val info = repository.parseAccountInfo()) {
                is Response.Success -> {
                    launch {
                        uiState.update {
                            it.copy(
                                name = info.data.givenName ?: "",
                                surname = info.data.familyName ?: "",
                                email = info.data.email,
                                sub = info.data.sub
                            )
                        }
                    }
                    launch {
                        getAvatarLink(info.data.sub)
                    }
                }

                is Response.Failure ->
                    SnackbarController.receiveEvent(SnackbarDefaultEvents.couldNotRefreshAccountInfo { retryUpdatingInfo() })
            }
        }
    }

    private suspend fun getAvatarLink(sub: String) {
        val avatarLink = repository.getImageLink(sub)
        uiState.update { it.copy(imageUrl = avatarLink) }
    }


    override fun onNameChange(newName: String) = uiState.update {
        it.copy(name = newName)
    }

    override fun onSurnameChange(newSurname: String) = uiState.update {
        it.copy(surname = newSurname)
    }

    override fun sendImage(data: ByteArray) {
        viewModelScope.launch {
            when (repository.sendImage(data)) {
                is Response.Success ->
                    SnackbarController.receiveEvent(successfulUpdateUserImageEvent)

                is Response.Failure -> SnackbarController.receiveEvent(couldNotUpdateUserImageEvent)
            }
        }
    }

    override fun deleteAccount() {
        viewModelScope.launch {
            if (repository.deactivateUser() is Response.Success) {
                repository.logOut()
            } else {
                SnackbarController.receiveEvent(couldNotDeleteAccountEvent { deleteAccount() })
            }
        }
    }

    private companion object {
        fun couldNotUpdateUserInfoEvent(onRetry: suspend () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotUpdateUserInfo,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = { onRetry() }
            )
        )

        fun couldNotDeleteAccountEvent(onRetry: () -> Unit) = SnackbarEvent(
            messageKey = CommonStringKeys.CouldNotDeleteAccount,
            action = SnackbarAction(
                nameKey = CommonStringKeys.Retry,
                effect = { onRetry() }
            )
        )

        val couldNotUpdateUserImageEvent =
            SnackbarEvent(messageKey = CommonStringKeys.CouldNotUpdateUserImage)
        val successfulUpdateUserImageEvent =
            SnackbarEvent(
                messageKey = CommonStringKeys.ImageUpdated,
                duration = SnackbarDuration.Short
            )
    }
}
