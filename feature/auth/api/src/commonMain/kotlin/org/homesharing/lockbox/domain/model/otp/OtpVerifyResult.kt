package org.homesharing.lockbox.domain.model.otp

data class OtpVerifyResult(
    val expiresIn: Int,
    val isNewUser: Boolean,
)
