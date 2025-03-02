package org.homesharing.lockbox.domain.model

import org.homesharing.lockbox.data.network.dto.SessionDto

fun SessionDto.toDomain(): Session = Session(
    ipAddress = ipAddress,
    lastAccessed = lastAccess
)
