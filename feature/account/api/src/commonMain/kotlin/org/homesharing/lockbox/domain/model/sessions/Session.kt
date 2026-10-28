package org.homesharing.lockbox.domain.model.sessions

data class Sessions(val sessions: List<Session>)

data class Session(
    val ipAddress: String,
    val lastActive: Long,
    val sessionId: String,
)
