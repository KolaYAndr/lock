package org.homesharing.lockbox.data.network.dto.password

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ChangePasswordRequestDto(
    @SerialName("current_password")
    val currentPassword: String,
    @SerialName("new_password")
    val newPassword: String,
)
