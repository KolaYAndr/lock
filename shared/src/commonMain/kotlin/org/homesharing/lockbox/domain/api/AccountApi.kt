package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.ApiResponse
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsResponseDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto
import org.homesharing.lockbox.data.network.dto.reset_password.ResetPasswordDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.token_response.TokenResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto

internal interface AccountApi {

    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): ApiResponse<SignUpResponseDto>

    suspend fun loadTokensInitially(
        username: String,
        password: String
    ): TokenResponseDto

    suspend fun refreshTokens(
        accessToken: String,
        refreshToken: String
    ): TokenResponseDto

    suspend fun getImageLink(sub: String): String

    suspend fun sendImage(
        accessToken: String,
        data: ByteArray
    )

    suspend fun getSessions(accessToken: String): ApiResponse<SessionsResponseDto>

    suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailResponseDto>

    suspend fun updateUserInfo(
        accessToken: String,
        email: String? = null,
        enabled: Boolean? = null,
        firstName: String? = null,
        lastName: String? = null
    ): ApiResponse<UpdateAccountResponseDto>

    suspend fun closeOtherSessions(
        accessToken: String,
        id: String
    ): ApiResponse<CloseSessionsResponseDto>

    suspend fun sendLinkToChangePassword(
        accessToken: String,
    ): ApiResponse<ResetPasswordDto>
}
