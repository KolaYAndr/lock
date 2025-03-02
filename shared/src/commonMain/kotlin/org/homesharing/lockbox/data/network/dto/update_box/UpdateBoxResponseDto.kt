package org.homesharing.lockbox.data.network.dto.update_box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UpdateBoxResponseDto(
    val body: UpdateBoxBody,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class UpdateBoxBody(
    val address: String,
    val alias: String,
    @SerialName("mac_address")
    val macAddress: String,
)