package org.homesharing.lockbox.presentation.activity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class LockboxActivityViewModel : ViewModel() {
    abstract val isLoggedIn: Flow<Boolean>
}
