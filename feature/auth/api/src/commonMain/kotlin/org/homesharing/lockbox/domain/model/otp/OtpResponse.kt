package org.homesharing.lockbox.domain.model.otp

data class OtpResponse(
    val resendAfter: Int,
    val expiresIn: Int,
)
