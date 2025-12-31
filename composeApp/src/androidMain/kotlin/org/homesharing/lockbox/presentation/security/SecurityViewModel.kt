package org.homesharing.lockbox.presentation.security

import androidx.lifecycle.ViewModel

internal abstract class SecurityViewModel : ViewModel() {
    abstract fun sendLinkToChangePassword()
}
