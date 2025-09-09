package org.homesharing.lockbox.data.network.dto.sign_up

import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequestDto(
    val password: String,
    val passwordRepeat: String,
    val email: String,
)
