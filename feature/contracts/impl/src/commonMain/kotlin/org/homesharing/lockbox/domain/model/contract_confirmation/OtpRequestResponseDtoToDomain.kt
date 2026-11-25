package org.homesharing.lockbox.domain.model.contract_confirmation

import org.homesharing.lockbox.data.network.dto.contract_confirmation.OtpRequestResponseDto

internal fun OtpRequestResponseDto.toDomain() = OtpRequestResponse(
    resendAfter = resendAfter,
    expiresIn = expiresIn,
)
