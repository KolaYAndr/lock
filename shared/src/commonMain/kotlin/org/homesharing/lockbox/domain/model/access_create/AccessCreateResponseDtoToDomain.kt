package org.homesharing.lockbox.domain.model.access_create

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.access_create.AccessCreateResponseDto
import org.homesharing.lockbox.domain.model.access.Access

internal fun AccessCreateResponseDto.toDomain(): Access = Access(
    name = access.alias,
    startTime = LocalDateTime.parse(access.startTime),
    endTime = LocalDateTime.parse(access.endTime),
    macAddress = access.macAddress,
    token = access.token,
    id = access.uuid
)
