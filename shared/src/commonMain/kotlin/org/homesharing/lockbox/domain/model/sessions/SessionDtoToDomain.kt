package org.homesharing.lockbox.domain.model.sessions

import org.homesharing.lockbox.data.network.dto.sessions.SessionDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto

internal fun SessionsDto.toDomain() = Sessions(sessions = sessions.map { it.toDomain() })

internal fun SessionDto.toDomain() = Session(
    ipAddress = ipAddress,
    lastAccessed = lastAccess,
    sessionId = id,
)
