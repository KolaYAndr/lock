package org.homesharing.lockbox.data.network.dto.sessions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SessionsDto(
    val sessions: List<SessionDto>,
)

@Serializable
internal data class SessionDto(
    val id: String,
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("last_active")
    val lastActive: String,
)