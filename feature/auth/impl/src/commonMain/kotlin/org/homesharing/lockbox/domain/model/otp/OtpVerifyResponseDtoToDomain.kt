package org.homesharing.lockbox.domain.model.otp

import org.homesharing.lockbox.data.network.dto.otp.OtpVerifyResponseDto

internal fun OtpVerifyResponseDto.toDomain() = OtpVerifyResult(
    expiresIn = expiresIn,
    isNewUser = isNewUser,
)
