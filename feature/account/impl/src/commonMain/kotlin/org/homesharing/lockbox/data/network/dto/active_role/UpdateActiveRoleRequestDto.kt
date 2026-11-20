package org.homesharing.lockbox.data.network.dto.active_role

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.role.UserRoleDto

@Serializable
internal data class UpdateActiveRoleRequestDto(
    @SerialName("active_role")
    val role: UserRoleDto,
)
