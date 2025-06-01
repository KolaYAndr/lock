package org.homesharing.lockbox.domain.model.access

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.accessed.AccessedDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessedResponseDto

internal fun AccessedResponseDto.toDomain(): Accesses = Accesses(
    accessList = listOfAccesses.map { accessedDto -> accessedDto.toDomain() }
)

private fun AccessedDto.toDomain(): Access = Access(
    name = alias,
    startTime = LocalDateTime.parse(startTime),
    endTime = LocalDateTime.parse(endTime),
    macAddress = macAddress,
    token = token
)
