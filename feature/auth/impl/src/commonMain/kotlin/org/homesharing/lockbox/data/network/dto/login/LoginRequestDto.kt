package org.homesharing.lockbox.data.network.dto.login

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRequestDto(
    val email: String,
    val password: String,
)
