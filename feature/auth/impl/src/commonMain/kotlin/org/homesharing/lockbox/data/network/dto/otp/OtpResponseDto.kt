package org.homesharing.lockbox.data.network.dto.otp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OtpResponseDto(
    @SerialName("resend_after")
    val resendAfter: Int,
    @SerialName("expires_in")
    val expiresIn: Int,
)
