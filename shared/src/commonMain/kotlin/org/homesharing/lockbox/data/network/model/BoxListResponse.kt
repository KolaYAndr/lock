package org.homesharing.lockbox.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxListResponse(
    val body: BoxListBody,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class BoxDto(
    val alias: String,
    @SerialName("mac_address")
    val macAddress: String,
    val address: String,
    val opened: Boolean,
    @SerialName("battery_level")
    val batteryLevel: Double,
    @SerialName("signal_strength")
    val signalStrength: Double
)

@Serializable
data class BoxListBody(
    val boxes: List<BoxDto>
)
