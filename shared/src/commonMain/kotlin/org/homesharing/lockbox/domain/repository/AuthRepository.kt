package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.confirm_email.ConfirmEmail

interface AuthRepository {
    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): Response<Unit>

    suspend fun loadTokensInitially(username: String, password: String): Response<Unit>

    suspend fun sendConfirmation(email: String): Response<ConfirmEmail>
}
