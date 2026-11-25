package org.homesharing.lockbox.data.network.dto.register

import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterRequestDto(
    val email: String,
    val password: String,
)
