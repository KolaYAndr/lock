package org.homesharing.lockbox

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

internal abstract class LockboxActivityViewModel : ViewModel() {
    abstract val loginState: StateFlow<LoginState>
}

internal sealed interface LoginState {
    object Loading : LoginState
    object LoggedIn : LoginState
    object NotLoggedIn : LoginState
}
