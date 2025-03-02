package org.homesharing.lockbox.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmEmailRequestDto(
    val mail: String
)