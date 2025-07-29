package org.homesharing.lockbox.data.network.dto.mockbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MockboxDto(
    @SerialName("mac_address")
    val macAddress: String,
    val alias: String,
)
