package org.homesharing.lockbox.presentation.account.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

internal abstract class AccountViewModel : ViewModel() {
    abstract val uiState: StateFlow<AccountUiState>
    abstract fun deleteAccount()
    abstract fun sendImage(data: ByteArray)
    abstract fun onSurnameChange(newSurname: String)
    abstract fun onNameChange(newName: String)
}


internal data class AccountUiState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val imageUrl: String = "",
    val sub: String = "",
)
