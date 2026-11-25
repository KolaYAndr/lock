package org.homesharing.lockbox.domain.model.otp

import org.homesharing.lockbox.data.network.dto.otp.OtpResponseDto

internal fun OtpResponseDto.toDomain() = OtpResponse(
    resendAfter = resendAfter,
    expiresIn = expiresIn,
)
