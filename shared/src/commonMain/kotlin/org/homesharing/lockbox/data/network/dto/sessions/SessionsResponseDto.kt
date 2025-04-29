package org.homesharing.lockbox.data.network.dto.sessions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SessionsResponseDto(
    val credentials: List<Credential>,
    val sessions: List<SessionDto>,
)

@Serializable
internal data class SessionDto(
    val id: String,
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("last_access")
    val lastAccess: Long,
    @SerialName("remember_me")
    val rememberMe: Boolean,
    val start: Long,
    @SerialName("transient_user")
    val transientUser: Boolean,
    @SerialName("user_id")
    val userId: String,
    val username: String,
    val clients: Map<String, String>
)

@Serializable
internal data class Credential(
    @SerialName("created_date")
    val createdDate: Long,
    val type: String,
    @SerialName("user_label")
    val userLabel: String?,
)
