package org.homesharing.lockbox.domain.model.sessions

data class Session(
    val ipAddress: String,
    val lastAccessed: Long,
    val sessionId: String,
)
