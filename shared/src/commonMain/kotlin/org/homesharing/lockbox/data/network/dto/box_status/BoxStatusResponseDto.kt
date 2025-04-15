package org.homesharing.lockbox.data.network.dto.box_status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BoxStatusResponseDto(
    @SerialName("battery_level")
    val batteryLevel: Double,
    @SerialName("mac_address")
    val macAddress: String,
    val opened: Boolean,
    @SerialName("signal_strength")
    val signalStrength: Double?,
    val timestamp: String,
)
