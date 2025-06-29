package org.homesharing.lockbox.data.repository

import io.ktor.util.decodeBase64String
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.dto.account_info.AccountInfoResponseDto
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

            if (!tokenResponse.error.isNullOrEmpty()) {
                return Response.Failure(tokenResponse.errorDescription!!)
            }

            val accessToken = tokenResponse.accessToken!!
            val refreshToken = tokenResponse.refreshToken!!

            with(tokenStorage) {
                saveAccessToken(accessToken)
                saveRefreshToken(refreshToken)
            }

            val accountInfo = decodeJWT(accessToken).toDomain()
            Response.Success(accountInfo)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.cause?.message.toString())
        }
    }


    private suspend fun decodeJWT(jwt: String): AccountInfoResponseDto = withContext(Dispatchers.Default) {
        val parts = jwt.split(".")
        val accountInfoDto = parts[1].decodeBase64String()

        Json.decodeFromString<AccountInfoResponseDto>(accountInfoDto)
    }

    override suspend fun logOut() = tokenStorage.clearInfo()


    override fun loggedIn(): Flow<Boolean> = tokenStorage.loggedIn()

    override suspend fun getImageLink(sub: String): String = api.getImageLink(sub)


    override suspend fun sendImage(data: ByteArray): Response<Unit> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse {
            api.sendImage(
                accessToken = tokenStorage.getAccessToken(),
                data = data
            )
        }
    }

    override suspend fun getSessions(): Response<Sessions> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = { api.getSessions(accessToken = tokenStorage.getAccessToken()) },
            mapper = SessionsResponseDto::toDomain
        )
    }

    override suspend fun updateInfo(
        name: String?,
        surname: String?,
        email: String?,
    ): Response<UpdateAccount> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
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
    }

    override suspend fun deactivateUser(): Response<UpdateAccount> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                api.updateUserInfo(
                    accessToken = tokenStorage.getAccessToken(),
                    enabled = false
                )
            },
            mapper = UpdateAccountResponseDto::toDomain
        )
    }

    override suspend fun closeOtherSessions(currentSessionId: String): Response<CloseSessions> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return try {
            Response.Success(
                api.closeOtherSessions(
                    accessToken = tokenStorage.getAccessToken(),
                    id = currentSessionId
                ).body!!.toDomain()
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message.orEmpty())
        }
    }

    override suspend fun sendLinkToChangeEmail(): Response<ResetPassword> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                api.sendLinkToChangePassword(
                    accessToken = tokenStorage.getAccessToken()
                )
            },
            mapper = ResetPasswordResponseDto::toDomain
        )
    }

    private suspend fun refreshToken() {
        val tokenResponse =
            api.refreshTokens(
                accessToken = tokenStorage.getAccessToken(),
                refreshToken = tokenStorage.getRefreshToken()
            )

        if (tokenResponse.error.isNullOrEmpty()) {
            val accessToken = tokenResponse.accessToken!!
            val refreshToken = tokenResponse.refreshToken!!
            val expiresIn = tokenResponse.expiresIn!!

            with(tokenStorage) {
                saveAccessToken(accessToken)
                saveRefreshToken(refreshToken)
                saveNextRefreshTime(expiresIn + refreshManager.currentTime)
            }
        }
    }
}
