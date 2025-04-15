package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.data.network.dto.token_response.TokenResponseDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.domain.api.response.Response

interface AuthRepository {

    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): Response<SignUpResponseDto>

    suspend fun loadTokensInitially(username: String, password: String): TokenResponseDto

    suspend fun sendConfirmation(email: String): Response<ConfirmEmailResponseDto>
}
