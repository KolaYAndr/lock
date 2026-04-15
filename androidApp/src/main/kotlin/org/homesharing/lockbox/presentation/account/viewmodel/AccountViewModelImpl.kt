package org.homesharing.lockbox.presentation.account.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar.SnackbarController
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotDeleteAccountEvent
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUpdateUserImageEvent
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUpdateUserInfoEvent
import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.successfulUpdateUserImageEvent

private const val UPDATE_TIME = 500L

internal class AccountViewModelImpl(
    private val repository: AccountRepository,
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
                .dropWhile { (name, surname) ->
                    name.isEmpty() && surname.isEmpty()
                }
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
            when (val accountInfo = repository.parseAccountInfo()) {
                is Response.Success -> {
                    uiState.update {
                        it.copy(
                            name = accountInfo.data.givenName.orEmpty(),
                            surname = accountInfo.data.familyName.orEmpty(),
                            email = accountInfo.data.email,
                            sub = accountInfo.data.sub,
                            imageUrl = repository.getImageLink(accountInfo.data.sub)
                        )
                    }
                }

                is Response.Failure ->
                    SnackbarController.receiveEvent(
                        SnackbarDefaultEvents.couldNotRefreshAccountInfo { retryUpdatingInfo() }
                    )
            }
        }
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
            when (repository.deactivateUser()) {
                is Response.Success -> repository.logOut()
                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotDeleteAccountEvent { deleteAccount() })
            }
        }
    }
}
