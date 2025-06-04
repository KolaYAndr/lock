package org.homesharing.lockbox.presentation.account

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarAction
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotRefreshAccountInfo
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarEvent

private const val UPDATE_TIME = 500L

internal class AccountViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<AccountUiState> = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> get() = _uiState.asStateFlow()


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

                    if (response is Response.Failure) {
                        SnackbarController.receiveEvent(couldNotUpdateUserInfoEvent { retryUpdatingInfo() })
                    }
                }
        }
    }

    private suspend fun retryUpdatingInfo() {
        val response = repository.updateInfo(
            name = uiState.value.name,
            surname = uiState.value.surname
        )

        if (response is Response.Failure) {
            SnackbarController.receiveEvent(couldNotUpdateUserInfoEvent { retryUpdatingInfo() })
        }
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            when (val info = repository.parseAccountInfo()) {
                is Response.Success -> {
                    launch {
                        _uiState.update {
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

                is Response.Failure -> {
                    SnackbarController.receiveEvent(couldNotRefreshAccountInfo { retryUpdatingInfo() })
                }
            }
        }
    }

    private suspend fun getAvatarLink(sub: String) {
        val avatarLink = repository.getImageLink(sub)
        _uiState.update { it.copy(imageUri = avatarLink) }
    }


    fun onNameChange(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
    }

    fun onSurnameChange(newSurname: String) {
        _uiState.update {
            it.copy(surname = newSurname)
        }
    }

    fun sendImage(data: ByteArray) {
        viewModelScope.launch {
            try {
                repository.sendImage(data)
                SnackbarController.receiveEvent(successfulUpdateUserImageEvent)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                SnackbarController.receiveEvent(couldNotUpdateUserImageEvent)
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            repository.deactivateUser()
        }
    }

    private companion object {
        fun couldNotUpdateUserInfoEvent(onRetry: suspend () -> Unit) = SnackbarEvent(
            message = R.string.could_not_update_user_info,
            action = SnackbarAction(
                name = R.string.retry,
                effect = { onRetry() }
            )
        )

        val couldNotUpdateUserImageEvent =
            SnackbarEvent(message = R.string.could_not_update_user_image)
        val successfulUpdateUserImageEvent =
            SnackbarEvent(message = R.string.image_updated, duration = SnackbarDuration.Short)
    }
}

internal data class AccountUiState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val imageUri: String = "",
    val sub: String = "",
)
