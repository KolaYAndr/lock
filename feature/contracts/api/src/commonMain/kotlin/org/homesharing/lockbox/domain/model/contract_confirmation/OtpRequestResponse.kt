package org.homesharing.lockbox.domain.model.contract_confirmation

data class OtpRequestResponse(
    val resendAfter: Int,
    val expiresIn: Int,
)
