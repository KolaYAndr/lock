package org.homesharing.lockbox.domain.model.role

import org.homesharing.lockbox.data.network.dto.role.UserRoleDto

internal fun UserRole.toDto(): UserRoleDto = when (this) {
    UserRole.Guest -> UserRoleDto.Guest
    UserRole.Landlord -> UserRoleDto.Landlord
}
