package org.homesharing.lockbox.domain.model.close_sessions

import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsResponseDto

internal fun CloseSessionsResponseDto.toDomain(): CloseSessions = CloseSessions(this.message)