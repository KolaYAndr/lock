package org.homesharing.lockbox.presentation_v2.role_chooser

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.homesharing.lockbox.domain.model.role.UserRole

internal class RoleChooserViewModel: ViewModel() {
    private val _pickedRole = MutableStateFlow<UserRole?>(null)
    val pickedRole = _pickedRole.asStateFlow()

    fun onPickRole(role: UserRole) {
        _pickedRole.update { role }
    }
}
