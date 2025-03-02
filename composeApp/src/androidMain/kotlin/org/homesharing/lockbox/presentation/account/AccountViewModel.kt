package org.homesharing.lockbox.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import org.homesharing.lockbox.domain.repository.AccountRepository

private const val UPDATE_TIME = 500L

class AccountViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<AccountUiState> = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> get() = _uiState.asStateFlow()

    private var initialLoadComplete = false

    init {
        fetchInfo()
        sendUpdates()
    }

    @OptIn(FlowPreview::class)
    private fun sendUpdates() {
        viewModelScope.launch {
            uiState
                .map { it.name to it.surname }
                .distinctUntilChanged()
                .debounce(UPDATE_TIME)
                .collectLatest { (name, surname) ->
                    if (initialLoadComplete) {
                        repository.updateInfo(name = name, surname = surname)
                    }
                }
        }
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            val info = repository.parseAccountInfo()

            _uiState.update {
                it.copy(
                    name = info.givenName ?: "",
                    surname = info.familyName ?: "",
                    email = info.email,
                    sub = info.sub
                )
            }
            getAvatarLink(info.sub)

            initialLoadComplete = true
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
            repository.sendImage(data)
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            repository.deactivateUser()
        }
    }
}

data class AccountUiState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val imageUri: String = "",
    val sub: String = "",
)
