package org.homesharing.lockbox.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxResponseDto(
    val body: BoxBody,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class BoxBody(
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
    val timestamp: String
)
