package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockBoxApiResponse
import org.homesharing.lockbox.domain.model.confirm_email.ConfirmEmail
import org.homesharing.lockbox.domain.model.confirm_email.toDomain
import org.homesharing.lockbox.domain.model.sign_up.SignUp
import org.homesharing.lockbox.domain.model.sign_up.toDomain
import org.homesharing.lockbox.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage,
    private val refreshManager: RefreshManager,
) : AuthRepository {
    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): Response<SignUp> =
        parseLockBoxApiResponse(
            apiCall = { api.signUp(username, password, passwordRepeated) },
            mapper = SignUpResponseDto::toDomain
        )

    override suspend fun loadTokensInitially(
        username: String,
        password: String,
    ): Response<Unit> =
        when (val response = api.loadTokensInitially(username, password)) {
            is AuthResponse.Success -> {
                with(tokenStorage) {
                    saveTokens(response.accessToken, response.refreshToken)
                    saveNextRefreshTime(response.expiresIn + refreshManager.currentTime)
                }
                Response.Success(Unit)
            }
            is AuthResponse.Error -> Response.Failure(response.errorDescription)
        }


    override suspend fun sendConfirmation(email: String): Response<ConfirmEmail> =
        parseLockBoxApiResponse(
            apiCall = { api.confirmEmail(email) },
            mapper = ConfirmEmailResponseDto::toDomain
        )
}
