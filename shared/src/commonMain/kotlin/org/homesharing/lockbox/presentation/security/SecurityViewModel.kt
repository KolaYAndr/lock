package org.homesharing.lockbox.presentation.security

import androidx.lifecycle.ViewModel

abstract class SecurityViewModel : ViewModel() {
    abstract fun sendLinkToChangePassword()
}
