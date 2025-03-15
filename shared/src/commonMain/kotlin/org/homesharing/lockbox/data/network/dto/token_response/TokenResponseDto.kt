package org.homesharing.lockbox.data.network.dto.token_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class TokenResponseDto {
    @Serializable
    data class TokenSuccessResponseDto(
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
        @SerialName("not-before-policy")
        val notBeforePolicy: Int,
        @SerialName("session_state")
        val sessionState: String,
        val scope: String,
    ) : TokenResponseDto()

    @Serializable
    data class TokenFailureResponseDto(
        @SerialName("error")
        val error: String,
        @SerialName("error_description")
        val errorDescription: String,
    ) : TokenResponseDto()
}
