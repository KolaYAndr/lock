package org.homesharing.lockbox.data.network.dto.password_reset

import kotlinx.serialization.Serializable

@Serializable
internal data class PasswordResetRequestDto(
    val email: String,
)
