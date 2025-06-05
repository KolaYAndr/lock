package org.homesharing.lockbox.domain.model.access

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.accessed.AccessDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessResponseDto

internal fun AccessResponseDto.toDomain(): Accesses = Accesses(
    accessList = listOfAccesses.map { accessedDto -> accessedDto.toDomain() }
)

internal fun AccessDto.toDomain(): Access = Access(
    name = alias,
    startTime = LocalDateTime.parse(startTime),
    endTime = LocalDateTime.parse(endTime),
    macAddress = macAddress,
    token = token,
    id = uuid
)
