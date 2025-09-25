package org.homesharing.lockbox.domain.model.box

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusResponseDto
import org.homesharing.lockbox.data.network.dto.box.StatusDto

internal fun BoxWithStatusResponseDto.toDomain(): BoxWithStatus =
    BoxWithStatus(box.toDomain(), status.toDomain())

internal fun BoxResponseDto.toDomain(): Box = Box(
    address = address,
    alias = alias,
    ownerSub = ownerSub,
    macAddress = macAddress
)

internal fun StatusDto.toDomain(): Status = Status(
    macAddress = macAddress,
    opened = opened,
    batteryLevel = batteryLevel,
    signalStrength = signalStrength,
    timeStamp = timestamp?.let { it -> LocalDateTime.parse(it) }
)
