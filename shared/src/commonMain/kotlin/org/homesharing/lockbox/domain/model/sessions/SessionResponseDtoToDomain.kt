package org.homesharing.lockbox.domain.model.sessions

import org.homesharing.lockbox.data.network.dto.sessions.SessionDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto

internal fun SessionsResponseDto.toDomain(): Sessions = Sessions(sessions = sessions.map { it.toDomain() })

internal fun SessionDto.toDomain(): Session = Session(
    ipAddress = ipAddress,
    lastAccessed = lastAccess,
    sessionId = id,
)
