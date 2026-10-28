package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto

internal interface AuthApi {
    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): ApiResponse<Unit>

    suspend fun loadTokensInitially(
        username: String,
        password: String,
    ): AuthResponse

    suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailDto>
}
