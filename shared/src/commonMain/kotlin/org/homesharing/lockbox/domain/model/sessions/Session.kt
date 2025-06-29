package org.homesharing.lockbox.domain.model.sessions

import kotlin.jvm.JvmInline

@JvmInline
value class Sessions(val sessions: List<Session>)

data class Session(
    val ipAddress: String,
    val lastAccessed: Long,
    val sessionId: String,
)
