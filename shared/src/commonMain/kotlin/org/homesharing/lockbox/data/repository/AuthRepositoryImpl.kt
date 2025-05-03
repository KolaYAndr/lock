package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.CancellationException
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.token_response.TokenResponseDto
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage,
    private val refreshManager: RefreshManager
) : AuthRepository {

    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): Response<SignUpResponseDto> {
        return try {
            val apiResponse = api.signUp(username, password, passwordRepeated)

            if (apiResponse.error) {
                Response.Failure(apiResponse.errorText!!)
            } else {
                Response.Success(apiResponse.body!!)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message.toString())
        }
    }

    override suspend fun loadTokensInitially(
        username: String,
        password: String
    ): TokenResponseDto {
        val response = api.loadTokensInitially(username, password)

        if (response.error.isNullOrEmpty()) {
            with(tokenStorage) {
                saveAccessToken(response.accessToken!!)
                saveRefreshToken(response.refreshToken!!)
                saveNextRefreshTime(response.expiresIn!! + refreshManager.currentTime)
            }
        }
        return response
    }


    override suspend fun sendConfirmation(email: String): Response<ConfirmEmailResponseDto> {
        return try {
            val apiResponse = api.confirmEmail(email)

            if (apiResponse.error) {
                Response.Failure(apiResponse.errorText!!)
            } else {
                Response.Success(apiResponse.body!!)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message.toString())
        }
    }
}
