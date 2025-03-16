package org.homesharing.lockbox.data.network.dto.token_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDto(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("expires_in")
    val expiresIn: Int? = null,
    @SerialName("refresh_expires_in")
    val refreshExpiresIn: Int? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("token_type")
    val tokenType: String? = null,
    @SerialName("not-before-policy")
    val notBeforePolicy: Int? = null,
    @SerialName("session_state")
    val sessionState: String? = null,
    val scope: String? = null,
    @SerialName("error")
    val error: String? = null,
    @SerialName("error_description")
    val errorDescription: String? = null,
)
