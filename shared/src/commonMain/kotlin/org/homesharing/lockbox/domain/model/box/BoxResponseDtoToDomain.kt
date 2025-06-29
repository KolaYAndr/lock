package org.homesharing.lockbox.domain.model.box

import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto

internal fun BoxResponseDto.toDomain(): Box = Box(
    address = address,
    alias = alias,
    batteryLevel = batteryLevel,
    macAddress = macAddress,
    opened = opened,
    signalStrength = signalStrength
)
