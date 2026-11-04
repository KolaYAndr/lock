package org.homesharing.lockbox.domain.model.role

import org.homesharing.lockbox.data.network.dto.role.UserRoleDto

internal fun UserRoleDto.toDomain(): UserRole = when (this) {
    UserRoleDto.Guest -> UserRole.Guest
    UserRoleDto.Landlord -> UserRole.Landlord
}
