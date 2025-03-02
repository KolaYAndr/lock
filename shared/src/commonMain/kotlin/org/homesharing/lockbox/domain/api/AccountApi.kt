package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.dto.ConfirmEmailRequestDto
import org.homesharing.lockbox.data.network.dto.InitialTokenLoadResponseDto
import org.homesharing.lockbox.data.network.dto.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.update_user.UpdateAccountResponseDto
import org.homesharing.lockbox.data.network.dto.update_user.UpdateAccountRequestDto
import org.homesharing.lockbox.data.network.token.model.TokenInfo

interface AccountApi {
    suspend fun signUp(username: String, password: String, passwordRepeated: String): SignUpResponseDto
    suspend fun loadTokensInitially(username: String, password: String): InitialTokenLoadResponseDto
    suspend fun refreshTokens(refreshToken: String): TokenInfo
    suspend fun getImageLinkBySub(sub: String) : String
    suspend fun sendImage(data: ByteArray)
    suspend fun getSessions(): SessionsResponseDto
    suspend fun confirmEmail(body: ConfirmEmailRequestDto)
    suspend fun updateUserInfo(body: UpdateAccountRequestDto): UpdateAccountResponseDto
}