package org.homesharing.lockbox.data.network.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal sealed interface AuthResponse {
    @Serializable
    data class Success(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("expires_in")
        val expiresIn: Int,
        @SerialName("refresh_expires_in")
        val refreshExpiresIn: Int,
        @SerialName("refresh_token")
        val refreshToken: String,
        @SerialName("token_type")
        val tokenType: String,
        @SerialName("id_token")
        val idToken: String,
        @SerialName("not-before-policy")
        val notBeforePolicy: Int,
        @SerialName("session_state")
        val sessionState: String,
        val scope: String,
    ): AuthResponse

    @Serializable
    data class Error(
        @SerialName("error")
        val error: String,
        @SerialName("error_description")
        val errorDescription: String,
    ): AuthResponse
}
