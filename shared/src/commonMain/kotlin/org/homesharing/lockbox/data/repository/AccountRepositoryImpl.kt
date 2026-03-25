package org.homesharing.lockbox.data.repository

import io.ktor.util.decodeBase64String
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.dto.account_info.AccountInfoResponseDto
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsResponseDto
import org.homesharing.lockbox.data.network.dto.reset_password.ResetPasswordResponseDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto
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
    private val json: Json,
) : AccountRepository {
    override suspend fun parseAccountInfo(): Response<AccountInfo> {
        return try {
            val token = tokenStorage.getAccessToken()
            if (token.isBlank()) return Response.Failure("Not logged in")
            val accountInfo = decodeJWT(token).toDomain()
            Response.Success(accountInfo)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message ?: "Unknown error")
        }
    }

    override suspend fun refreshAccount(): Response<AccountInfo> {
        return parseAccountInfo()
    }

    private suspend fun decodeJWT(jwt: String): AccountInfoResponseDto =
        withContext(Dispatchers.Default) {
            val parts = jwt.split(".")
            val accountInfoDto = parts[1].decodeBase64String()
            json.decodeFromString<AccountInfoResponseDto>(accountInfoDto)
        }

    override suspend fun logOut() = tokenStorage.clearInfo()

    override val loggedIn: Flow<Boolean> = tokenStorage.loggedIn

    override suspend fun getImageLink(sub: String): String = api.getImageLink(sub)

    override suspend fun sendImage(data: ByteArray): Response<Unit> =
        parseLockBoxApiResponse {
            api.sendImage(data = data)
        }

    override suspend fun getSessions(): Response<Sessions> =
        parseLockBoxApiResponse(
            apiCall = { api.getSessions() },
            mapper = SessionsResponseDto::toDomain
        )

    override suspend fun updateInfo(
        name: String?,
        surname: String?,
        email: String?,
    ): Response<UpdateAccount> =
        parseLockBoxApiResponse(
            apiCall = {
                api.updateUserInfo(
                    email = email,
                    firstName = name,
                    lastName = surname
                )
            },
            mapper = UpdateAccountResponseDto::toDomain
        )

    override suspend fun deactivateUser(): Response<UpdateAccount> =
        parseLockBoxApiResponse(
            apiCall = {
                api.updateUserInfo(enabled = false)
            },
            mapper = UpdateAccountResponseDto::toDomain
        )

    override suspend fun closeOtherSessions(currentSessionId: String): Response<CloseSessions> =
        parseLockBoxApiResponse(
            apiCall = {
                api.closeOtherSessions(id = currentSessionId)
            },
            mapper = CloseSessionsResponseDto::toDomain
        )

    override suspend fun sendLinkToChangeEmail(): Response<ResetPassword> =
        parseLockBoxApiResponse(
            apiCall = {
                api.sendLinkToChangePassword()
            },
            mapper = ResetPasswordResponseDto::toDomain
        )
}
