package org.homesharing.lockbox.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxStatusResponse(
    val body: StatusBody,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class StatusBody(
    @SerialName("battery_level")
    val batteryLevel: Double,
    @SerialName("mac_address")
    val macAddress: String,
    val opened: Boolean?,
    @SerialName("signal_strength")
    val signalStrength: Double?,
    val timestamp: String
)