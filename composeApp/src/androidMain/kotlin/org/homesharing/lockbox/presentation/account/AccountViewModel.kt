package org.homesharing.lockbox.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

class AccountViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<AccountUiState> = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> get() = _uiState.asStateFlow()

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
        }
    }

    private suspend fun getAvatarLink(sub: String) {
        val avatarLink =  repository.getAvatarLink(sub)
        _uiState.update { it.copy(imageUri = avatarLink) }
    }

    init {
        fetchInfo()
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

    fun onSurnameChanged() {

    }

    fun onNameChanged() {

    }

    fun sendImage(data: ByteArray) {
        viewModelScope.launch {
            repository.sendImage(data)
            getAvatarLink(uiState.value.sub)
        }
    }

    fun showDialog() {
        _uiState.update { it.copy(showDialog = true) }
    }

    fun hideDialog() {
        _uiState.update { it.copy(showDialog = false) }
    }

    fun deleteAccount() {

    }
}

data class AccountUiState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val showDialog: Boolean = false,
    val imageUri: String = "",
    val sub: String = "",
)
