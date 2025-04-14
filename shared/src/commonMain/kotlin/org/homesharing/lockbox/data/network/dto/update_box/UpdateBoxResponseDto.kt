package org.homesharing.lockbox.data.network.dto.update_box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateBoxResponseDto(
    val address: String,
    val alias: String,
    @SerialName("mac_address")
    val macAddress: String,
)
