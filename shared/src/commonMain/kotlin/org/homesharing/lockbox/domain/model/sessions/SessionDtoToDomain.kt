package org.homesharing.lockbox.domain.model.sessions

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.sessions.SessionDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto

internal fun SessionsDto.toDomain() = Sessions(sessions = sessions.map { it.toDomain() })

internal fun SessionDto.toDomain() = Session(
    ipAddress = ip,
    lastActive = LocalDateTime.parse(lastActive),
    sessionId = id,
)
