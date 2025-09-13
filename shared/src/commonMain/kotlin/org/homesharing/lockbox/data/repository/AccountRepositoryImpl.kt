package org.homesharing.lockbox.data.repository

import io.ktor.util.decodeBase64String
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.account_info.AccountInfoResponseDto
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsResponseDto
import org.homesharing.lockbox.data.network.dto.reset_password.ResetPasswordResponseDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockBoxApiResponse
import org.homesharing.lockbox.domain.model.account_info.AccountInfo
import org.homesharing.lockbox.domain.model.account_info.toDomain
import org.homesharing.lockbox.domain.model.close_sessions.CloseSessions
import org.homesharing.lockbox.domain.model.close_sessions.toDomain
import org.homesharing.lockbox.domain.model.reset_password.ResetPassword
import org.homesharing.lockbox.domain.model.reset_password.toDomain
import org.homesharing.lockbox.domain.model.sessions.Sessions
import org.homesharing.lockbox.domain.model.sessions.toDomain
import org.homesharing.lockbox.domain.model.update_account.UpdateAccount
import org.homesharing.lockbox.domain.model.update_account.toDomain
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class AccountRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage,
    private val refreshManager: RefreshManager,
    private val json: Json,
) : AccountRepository {
    override suspend fun parseAccountInfo(): Response<AccountInfo> {
        return try {
            val token = tokenStorage.getAccessToken()
            val accountInfo = decodeJWT(token).toDomain()
            Response.Success(accountInfo)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.cause?.message.toString())
        }
    }

    override suspend fun refreshAccount(): Response<AccountInfo> {
        return try {
            val tokenResponse =
                api.refreshTokens(
                    accessToken = tokenStorage.getAccessToken(),
                    refreshToken = tokenStorage.getRefreshToken()
                )

            return when (tokenResponse) {
                is AuthResponse.Error -> Response.Failure(tokenResponse.errorDescription)
                is AuthResponse.Success -> {
                    val accessToken = tokenResponse.accessToken
                    val refreshToken = tokenResponse.refreshToken
                    val expiresIn = tokenResponse.expiresIn

                    with(tokenStorage) {
                        saveTokens(accessToken, refreshToken)
                        saveNextRefreshTime(expiresIn + refreshManager.currentTime)
                    }

                    val accountInfo = decodeJWT(accessToken).toDomain()
                    Response.Success(accountInfo)
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.cause?.message.toString())
        }
    }


    private suspend fun decodeJWT(jwt: String): AccountInfoResponseDto =
        withContext(Dispatchers.Default) {
            val parts = jwt.split(".")
            val accountInfoDto = parts[1].decodeBase64String()

            json.decodeFromString<AccountInfoResponseDto>(accountInfoDto)
        }

    override suspend fun logOut() = tokenStorage.clearInfo()


    override fun loggedIn(): Flow<Boolean> = tokenStorage.loggedIn()

    override suspend fun getImageLink(sub: String): String = api.getImageLink(sub)


    override suspend fun sendImage(data: ByteArray): Response<Unit> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse {
                api.sendImage(
                    accessToken = tokenStorage.getAccessToken(),
                    data = data
                )
            }
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun getSessions(): Response<Sessions> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = { api.getSessions(accessToken = tokenStorage.getAccessToken()) },
                mapper = SessionsResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun updateInfo(
        name: String?,
        surname: String?,
        email: String?,
    ): Response<UpdateAccount> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    api.updateUserInfo(
                        accessToken = tokenStorage.getAccessToken(),
                        email = email,
                        firstName = name,
                        lastName = surname
                    )
                },
                mapper = UpdateAccountResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun deactivateUser(): Response<UpdateAccount> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    api.updateUserInfo(
                        accessToken = tokenStorage.getAccessToken(),
                        enabled = false
                    )
                },
                mapper = UpdateAccountResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun closeOtherSessions(currentSessionId: String): Response<CloseSessions> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    api.closeOtherSessions(
                        accessToken = tokenStorage.getAccessToken(),
                        id = currentSessionId
                    )
                },
                mapper = CloseSessionsResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun sendLinkToChangeEmail(): Response<ResetPassword> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    api.sendLinkToChangePassword(
                        accessToken = tokenStorage.getAccessToken()
                    )
                },
                mapper = ResetPasswordResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }


    private suspend fun refreshTokenIfNeed() {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            refreshToken()
        }
    }

    private suspend fun refreshToken() {
        val tokenResponse =
            api.refreshTokens(
                accessToken = tokenStorage.getAccessToken(),
                refreshToken = tokenStorage.getRefreshToken()
            )

        if (tokenResponse is AuthResponse.Success) {
            val accessToken = tokenResponse.accessToken
            val refreshToken = tokenResponse.refreshToken
            val expiresIn = tokenResponse.expiresIn

            with(tokenStorage) {
                saveTokens(accessToken, refreshToken)
                saveNextRefreshTime(expiresIn + refreshManager.currentTime)
            }
        }
    }
}
