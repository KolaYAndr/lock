package org.homesharing.lockbox.data.network.dto.role

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class UserRoleDto {
    @SerialName("guest")
    Guest,

    @SerialName("landlord")
    Landlord,
}
