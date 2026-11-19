package org.homesharing.lockbox.data.network.dto.password_reset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PasswordResetConfirmRequestDto(
    val email: String,
    val code: String,
    @SerialName("new_password")
    val newPassword: String,
)
