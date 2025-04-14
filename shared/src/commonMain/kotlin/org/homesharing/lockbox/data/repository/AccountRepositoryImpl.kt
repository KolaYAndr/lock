package org.homesharing.lockbox.data.repository

import io.ktor.util.decodeBase64String
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.dto.account_info.AccountInfoDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockBoxApiResponse
import org.homesharing.lockbox.domain.model.account_info.AccountInfo
import org.homesharing.lockbox.domain.model.account_info.toDomain
import org.homesharing.lockbox.domain.model.sessions.Session
import org.homesharing.lockbox.domain.model.sessions.toDomain
import org.homesharing.lockbox.domain.model.update_account.UpdateAccount
import org.homesharing.lockbox.domain.model.update_account.toDomain
import org.homesharing.lockbox.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage
) : AccountRepository {

    override suspend fun parseAccountInfo(): Response<AccountInfo> =
        withContext(Dispatchers.IO) {
            try {
                val token = tokenStorage.getAccessToken()
                val accountInfo = decodeJWT(token).toDomain()
                Response.Success(accountInfo)
            } catch (e: Exception) {
                Response.Failure(e.cause?.message.toString())
            }
        }

    override suspend fun refreshAccount(): Response<AccountInfo> = withContext(Dispatchers.IO) {
        try {
            val tokenResponse = api.refreshTokens(tokenStorage.getRefreshToken())

            if (!tokenResponse.error.isNullOrEmpty()) {
                return@withContext Response.Failure(tokenResponse.errorDescription!!)
            }

            val accessToken = tokenResponse.accessToken!!
            val refreshToken = tokenResponse.refreshToken!!

            with(tokenStorage) {
                saveAccessToken(accessToken)
                saveRefreshToken(refreshToken)
            }

            val accountInfo = decodeJWT(accessToken).toDomain()
            Response.Success(accountInfo)
        } catch (e: Exception) {
            Response.Failure(e.cause?.message.toString())
        }
    }


    private suspend fun decodeJWT(jwt: String): AccountInfoDto = withContext(Dispatchers.Default) {
        val parts = jwt.split(".")
        val accountInfoDto = parts[1].decodeBase64String()

        Json.decodeFromString<AccountInfoDto>(accountInfoDto)
    }

    override suspend fun logOut() = withContext(Dispatchers.IO) {
        tokenStorage.clearInfo()
    }

    override fun loggedIn(): Flow<Boolean> = tokenStorage.loggedIn()

    override suspend fun getImageLink(sub: String): String = withContext(Dispatchers.IO) {
        api.getImageLink(sub)
    }

    override suspend fun sendImage(data: ByteArray) = withContext(Dispatchers.IO) {
        api.sendImage(data)
    }

    override suspend fun getSessions(): Response<List<Session>> = withContext(Dispatchers.IO) {
        parseLockBoxApiResponse(
            apiCall = { api.getSessions() },
            mapper = SessionsResponseDto::toDomain
        )
    }

    override suspend fun updateInfo(
        name: String?,
        surname: String?,
        email: String?
    ): Response<UpdateAccount> = withContext(Dispatchers.IO) {
        parseLockBoxApiResponse(
            apiCall = { api.updateUserInfo(email = email, firstName = name, lastName = surname) },
            mapper = UpdateAccountResponseDto::toDomain
        )
    }

    override suspend fun deactivateUser(): Response<UpdateAccount> =
        withContext(Dispatchers.IO) {
            parseLockBoxApiResponse(
                apiCall = { api.updateUserInfo(enabled = false) },
                mapper = UpdateAccountResponseDto::toDomain
            )
        }

    override suspend fun closeOtherSessions(currentSessionId: String): Response<String> =
        withContext(Dispatchers.IO) {
            try {
                Response.Success(api.closeOtherSessions(currentSessionId).body!!.message)
            } catch (e: Exception) {
                Response.Failure(e.message.toString())
            }
        }
}
