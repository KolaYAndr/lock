package org.homesharing.lockbox.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxListResponseDto(
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
    @SerialName("owner_sub")
    val ownerSub: String,
    val address: String,
    val opened: Boolean,
    @SerialName("battery_level")
    val batteryLevel: Double,
    @SerialName("signal_strength")
    val signalStrength: Double,
    @SerialName("timestamp")
    val timestamp: String
)

@Serializable
data class BoxListBody(
    val boxes: List<BoxDto>,
    @SerialName("total_boxes")
    val totalBoxes: Int,
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("boxes_on_page")
    val boxesOnPage: Int,
)
