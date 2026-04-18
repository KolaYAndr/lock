package org.homesharing.lockbox.domain.model.access

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessesDto

internal fun AccessesDto.toDomain() = Accesses(
    accessList = listOfAccesses.map { accessedDto -> accessedDto.toDomain() }
)

internal fun AccessDto.toDomain() = Access(
    name = alias,
    startTime = LocalDateTime.parse(startTime),
    endTime = LocalDateTime.parse(endTime),
    macAddress = macAddress,
    id = uuid
)
