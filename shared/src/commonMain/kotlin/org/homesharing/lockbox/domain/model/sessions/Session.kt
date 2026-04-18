package org.homesharing.lockbox.domain.model.sessions

import kotlinx.datetime.LocalDateTime

data class Sessions(val sessions: List<Session>)

data class Session(
    val ipAddress: String,
    val lastActive: LocalDateTime,
    val sessionId: String,
)
