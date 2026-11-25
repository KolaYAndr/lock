package org.homesharing.lockbox.presentation.setup_profile_flow.name_setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class NameSetupViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _nameSetupState = MutableStateFlow(NameSetupState.Initial)
    val profileSetup = _nameSetupState.asStateFlow()

    private val _onSuccess = MutableSharedFlow<Unit>()
    val onSuccess: SharedFlow<Unit> = _onSuccess.asSharedFlow()

    fun onIntent(action: NameSetupAction) {
        when (action) {
            is NameSetupAction.FirstNameChanged ->
                _nameSetupState.update { it.copy(firstName = action.value) }

            is NameSetupAction.LastNameChanged ->
                _nameSetupState.update { it.copy(lastName = action.value) }

            is NameSetupAction.Proceed -> proceed()
        }
    }

    private fun proceed() {
        viewModelScope.launch {
//            val response = accountRepository.updateProfileInfo(
//                firstName = _nameSetupState.value.firstName,
//                lastName = _nameSetupState.value.lastName,
//            )
//
//            if (response is Response.Success) {
                _onSuccess.emit(Unit)
//            }
        }
    }
}

    internal data class NameSetupState(
        val firstName: String,
        val lastName: String,
    ) {

        companion object {
            val Initial = NameSetupState(
                firstName = "",
                lastName = "",
            )
        }
    }

    internal sealed interface NameSetupAction {
        data class FirstNameChanged(val value: String) : NameSetupAction
        data class LastNameChanged(val value: String) : NameSetupAction
        data object Proceed : NameSetupAction
    }
