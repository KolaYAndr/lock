package org.homesharing.lockbox.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.data.network.model.SignUpRequest
import org.homesharing.lockbox.domain.repository.LockBoxRepository

class SignUpViewModel(private val repository: LockBoxRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState get() = _uiState.asStateFlow()

    fun singUp() {
        viewModelScope.launch {
            val request = SignUpRequest(
                userName = uiState.value.userName,
                password = uiState.value.password,
                repeatPassword = uiState.value.password,
                email = uiState.value.email
            )
            val response = repository.signUp(
                request
            )
            if (response.error == true) {
                _uiState.update { it.copy(errorText = response.errorText) }
            }
        }
    }
}

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val userName: String = "",
    val errorText: String? = null
)