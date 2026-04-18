package org.homesharing.lockbox.domain.model.box

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.box.BoxDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusDto
import org.homesharing.lockbox.data.network.dto.box.StatusDto

internal fun BoxWithStatusDto.toDomain() =
    BoxWithStatus(box.toDomain(), status.toDomain())

internal fun BoxDto.toDomain() = Box(
    address = address,
    alias = alias,
    ownerSub = ownerSub,
    macAddress = macAddress
)

internal fun StatusDto.toDomain() = Status(
    macAddress = macAddress,
    opened = opened,
    batteryLevel = batteryLevel,
    signalStrength = signalStrength,
    timeStamp = timestamp?.let { LocalDateTime.parse(it) }
)
