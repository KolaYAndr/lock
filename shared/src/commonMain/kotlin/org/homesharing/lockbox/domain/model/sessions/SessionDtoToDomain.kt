package org.homesharing.lockbox.domain.model.sessions

import org.homesharing.lockbox.data.network.dto.sessions.SessionDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto

fun SessionsResponseDto.toDomain(): List<Session> = sessions.map { it.toDomain() }

fun SessionDto.toDomain(): Session = Session(
    ipAddress = ipAddress,
    lastAccessed = lastAccess,
    sessionId = id,
)
