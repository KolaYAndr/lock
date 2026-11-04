package org.homesharing.lockbox.domain.model.sessions

import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto

internal fun SessionsDto.toDomain() = Sessions(
    sessions = sessions.map { it.toDomain() }
)

internal fun SessionsDto.SessionDto.toDomain() = Sessions.Session(
    id = id,
    ip = ip,
    userAgent = userAgent,
    startedAt = startedAt,
    lastAccessAt = lastAccessAt,
    isCurrent = isCurrent,
)
