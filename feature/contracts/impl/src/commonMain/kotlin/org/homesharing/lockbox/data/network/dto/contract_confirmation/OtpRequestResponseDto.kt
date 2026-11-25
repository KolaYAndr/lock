package org.homesharing.lockbox.data.network.dto.contract_confirmation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OtpRequestResponseDto(
    @SerialName("resend_after")
    val resendAfter: Int,
    @SerialName("expires_in")
    val expiresIn: Int,
)
