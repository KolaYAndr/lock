package org.homesharing.lockbox.data.network.dto.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BoxResponseDto(
    val address: String,
    val alias: String,
    @SerialName("owner_sub")
    val ownerSub: String,
    @SerialName("battery_level")
    val batteryLevel: Double,
    @SerialName("mac_address")
    val macAddress: String,
    val opened: Boolean,
    @SerialName("signal_strength")
    val signalStrength: Double,
    @SerialName("timestamp")
    val timestamp: String,
)
