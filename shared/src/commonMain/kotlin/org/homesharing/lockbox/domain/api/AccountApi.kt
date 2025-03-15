package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.ApiResponse
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailRequestDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountRequestDto
import org.homesharing.lockbox.data.network.dto.token_response.TokenResponseDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto

interface AccountApi {

    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): ApiResponse<SignUpResponseDto>

    suspend fun loadTokensInitially(username: String, password: String): TokenResponseDto.TokenSuccessResponseDto

    suspend fun refreshTokens(refreshToken: String): TokenResponseDto.TokenSuccessResponseDto

    suspend fun getImageLinkBySub(sub: String): String

    suspend fun sendImage(data: ByteArray)

    suspend fun getSessions(): ApiResponse<SessionsResponseDto>

    suspend fun confirmEmail(body: ConfirmEmailRequestDto): ApiResponse<ConfirmEmailResponseDto>

    suspend fun updateUserInfo(body: UpdateAccountRequestDto): ApiResponse<UpdateAccountResponseDto>
}
