package org.homesharing.lockbox.data.network.dto.otp

import kotlinx.serialization.Serializable

@Serializable
internal data class OtpRequestDto(
    val email: String,
)
