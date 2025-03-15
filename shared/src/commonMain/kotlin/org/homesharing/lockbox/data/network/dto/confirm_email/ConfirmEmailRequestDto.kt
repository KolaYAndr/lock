package org.homesharing.lockbox.data.network.dto.confirm_email

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmEmailRequestDto(
    val mail: String,
)
