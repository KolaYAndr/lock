package org.homesharing.lockbox.presentation.warning

import androidx.lifecycle.ViewModel

abstract class WarningViewModel : ViewModel() {
    abstract fun logOut()
}