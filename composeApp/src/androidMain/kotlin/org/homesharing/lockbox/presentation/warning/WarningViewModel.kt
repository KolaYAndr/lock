package org.homesharing.lockbox.presentation.warning

import androidx.lifecycle.ViewModel

internal abstract class WarningViewModel : ViewModel() {
    abstract fun logOut()
}