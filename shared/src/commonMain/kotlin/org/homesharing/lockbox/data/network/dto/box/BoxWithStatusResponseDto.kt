package org.homesharing.lockbox.data.network.dto.box

import kotlinx.serialization.Serializable

@Serializable
internal data class BoxWithStatusResponseDto(
    val box: BoxResponseDto,
    val status: StatusDto
)

@Serializable
internal data class BoxResponseDto(
    val address: String,
    val alias: String,
    val ownerSub: String,
    val macAddress: String,
)

@Serializable
internal data class StatusDto(
    val macAddress: String,
    val opened: Boolean?,
    val batteryLevel: Double?,
    val signalStrength: Double?,
    val timestamp: String?
)
