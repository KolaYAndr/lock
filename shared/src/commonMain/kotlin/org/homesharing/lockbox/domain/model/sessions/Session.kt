package org.homesharing.lockbox.domain.model.sessions

data class Sessions(val sessions: List<Session>)

data class Session(
    val ipAddress: String,
    val lastAccessed: Long,
    val sessionId: String,
)
