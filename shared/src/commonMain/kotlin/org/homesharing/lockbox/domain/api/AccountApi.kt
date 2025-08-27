package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.AuthResponse

internal interface AccountApi {
    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): ApiResponse

    suspend fun loadTokensInitially(
        username: String,
        password: String
    ): AuthResponse

    suspend fun refreshTokens(
        accessToken: String,
        refreshToken: String
    ): AuthResponse

    suspend fun getImageLink(sub: String): String

    suspend fun sendImage(
        accessToken: String,
        data: ByteArray
    ): ApiResponse

    suspend fun getSessions(accessToken: String): ApiResponse

    suspend fun confirmEmail(email: String): ApiResponse

    suspend fun updateUserInfo(
        accessToken: String,
        email: String? = null,
        enabled: Boolean? = null,
        firstName: String? = null,
        lastName: String? = null
    ): ApiResponse

    suspend fun closeOtherSessions(
        accessToken: String,
        id: String
    ): ApiResponse

    suspend fun sendLinkToChangePassword(
        accessToken: String,
    ): ApiResponse
}
