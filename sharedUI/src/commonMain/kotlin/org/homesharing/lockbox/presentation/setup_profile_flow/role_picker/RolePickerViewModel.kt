package org.homesharing.lockbox.presentation.setup_profile_flow.role_picker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.homesharing.lockbox.domain.model.role.UserRole

internal class RolePickerViewModel: ViewModel() {
    private val _pickedRole = MutableStateFlow<UserRole?>(null)
    val pickedRole = _pickedRole.asStateFlow()

    fun onPickRole(role: UserRole) {
        _pickedRole.update { role }
    }
}
