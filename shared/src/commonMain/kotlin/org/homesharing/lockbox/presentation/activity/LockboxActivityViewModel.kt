package org.homesharing.lockbox.presentation.activity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class LockboxActivityViewModel : ViewModel() {
    abstract val loginState: StateFlow<LoginState>
}

sealed interface LoginState {
    object Loading : LoginState
    object LoggedIn : LoginState
    object NotLoggedIn : LoginState
}
