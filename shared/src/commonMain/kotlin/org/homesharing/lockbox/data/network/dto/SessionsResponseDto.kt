package org.homesharing.lockbox.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionsResponseDto(
    val body: SessionsBody,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class SessionsBody(
    val credentials: List<Credential>,
    val sessions: List<SessionDto>
)

@Serializable
data class SessionDto(
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
    val username: String
)

@Serializable
data class Credential(
    @SerialName("created_date")
    val createdDate: Long,
    val type: String,
    @SerialName("user_label")
    val userLabel: String
)
