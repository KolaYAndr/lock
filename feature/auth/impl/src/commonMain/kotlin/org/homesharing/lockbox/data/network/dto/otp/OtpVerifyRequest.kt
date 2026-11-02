package org.homesharing.lockbox.data.network.dto.otp

import kotlinx.serialization.Serializable

@Serializable
internal data class OtpVerifyRequest(
    val email: String,
    val code: String,
)
