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

    suspend fun getImageLink(sub: String): String

    suspend fun sendImage(
        data: ByteArray
    ): ApiResponse

    suspend fun getSessions(): ApiResponse

    suspend fun confirmEmail(email: String): ApiResponse

    suspend fun updateUserInfo(
        email: String? = null,
        enabled: Boolean? = null,
        firstName: String? = null,
        lastName: String? = null
    ): ApiResponse

    suspend fun closeOtherSessions(
        id: String
    ): ApiResponse
}
