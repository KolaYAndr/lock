package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.AuthApi
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import org.homesharing.lockbox.data.network.token.TokenStorage
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.confirm_email.ConfirmEmail
import org.homesharing.lockbox.domain.model.confirm_email.toDomain
import org.homesharing.lockbox.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage,
) : AuthRepository {
    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): Response<Unit> = parseLockboxApiResponse { api.signUp(username, password, passwordRepeated) }

    override suspend fun loadTokensInitially(
        username: String,
        password: String,
    ): Response<Unit> {
        return try {
            when (val response = api.loadTokensInitially(username, password)) {
                is AuthResponse.Success -> {
                    with(tokenStorage) {
                        saveTokens(response.accessToken, response.refreshToken)
                    }
                    Response.Success(Unit)
                }
                is AuthResponse.Error -> Response.Failure(response.errorDescription)
            }
        } catch (t: Throwable) {
            Response.Failure(t.message.orEmpty())
        }
    }

    override suspend fun sendConfirmation(email: String): Response<ConfirmEmail> =
        parseLockboxApiResponse(
            apiCall = { api.confirmEmail(email) },
            mapper = ConfirmEmailDto::toDomain
        )
}
