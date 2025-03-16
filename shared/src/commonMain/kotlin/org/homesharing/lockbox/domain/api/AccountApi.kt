package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.ApiResponse
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.token_response.TokenResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto

interface AccountApi {

    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): ApiResponse<SignUpResponseDto>

    suspend fun loadTokensInitially(username: String, password: String): TokenResponseDto

    suspend fun refreshTokens(refreshToken: String): TokenResponseDto

    suspend fun getImageLinkBySub(sub: String): String

    suspend fun sendImage(data: ByteArray)

    suspend fun getSessions(): ApiResponse<SessionsResponseDto>

    suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailResponseDto>

    suspend fun updateUserInfo(
        email: String? = null,
        enabled: Boolean? = null,
        firstName: String? = null,
        lastName: String? = null
    ): ApiResponse<UpdateAccountResponseDto>
}
