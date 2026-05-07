package org.homesharing.lockbox.presentation_v2.profile_setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class ProfileSetupViewModel(
    private val accountRepository: AccountRepository
) : ViewModel(){
    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    fun onChangeFullName(newFullName: String) {
        _fullName.update { newFullName }
    }

    fun proceed() {
        viewModelScope.launch {
            accountRepository.updateProfileInfo(
                fullName = fullName.value
            )
        }
    }
}