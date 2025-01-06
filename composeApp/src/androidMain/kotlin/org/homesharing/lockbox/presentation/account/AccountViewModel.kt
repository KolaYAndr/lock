package org.homesharing.lockbox.presentation.account

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AccountViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<AccountUiState> = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> get() = _uiState.asStateFlow()

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
    val name: String = "Иван",
    val surname: String = "Иванов",
    val email: String = "ivan.ivanov@email.com",
    val phoneNumber: String = "",
    val showDialog: Boolean = false
)