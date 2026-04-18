package org.homesharing.lockbox.presentation.account.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

internal abstract class AccountViewModel : ViewModel() {
    abstract val uiState: StateFlow<AccountUiState>
    abstract fun deleteAccount()
    abstract fun sendImage(data: ByteArray)
    abstract fun onNameChange(newName: String)
    abstract fun updateProfileName()
}

internal data class AccountUiState(
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val imageUrl: String = "",
)
