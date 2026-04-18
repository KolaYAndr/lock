package org.homesharing.lockbox.domain.model.close_sessions

import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsDto

internal fun CloseSessionsDto.toDomain() = CloseSessions(result = message)
