package org.homesharing.lockbox.data.network.dto.sessions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SessionsDto(
    val sessions: List<SessionDto>,
) {
    @Serializable
    internal data class SessionDto(
        val id: String,
        val ip: String,
        @SerialName("user_agent")
        val userAgent: String,
        @SerialName("started_at")
        val startedAt: String,
        @SerialName("last_access_at")
        val lastAccessAt: String,
        @SerialName("is_current")
        val isCurrent: Boolean,
    )
}
