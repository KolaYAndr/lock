package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.dto.account_info.AccountInfoDto
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockBoxApiResponse
import org.homesharing.lockbox.domain.model.account_info.AccountInfo
import org.homesharing.lockbox.domain.model.account_info.toDomain
import org.homesharing.lockbox.domain.model.close_sessions.CloseSessions
import org.homesharing.lockbox.domain.model.close_sessions.toDomain
import org.homesharing.lockbox.domain.model.profile_info.ProfileInfo
import org.homesharing.lockbox.domain.model.profile_info.toDomain
import org.homesharing.lockbox.domain.model.sessions.Sessions
import org.homesharing.lockbox.domain.model.sessions.toDomain
import org.homesharing.lockbox.domain.model.update_account.UpdateAccount
import org.homesharing.lockbox.domain.model.update_account.toDomain
import org.homesharing.lockbox.domain.repository.AccountRepository
import kotlin.io.encoding.Base64

internal class AccountRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage,
    private val json: Json,
) : AccountRepository {
    private var sub = ""

    override suspend fun parseAccountInfo(): Response<AccountInfo> {
        return try {
            val token = tokenStorage.getAccessToken()
            if (token.isBlank()) return Response.Failure("Not logged in")
            val accountInfo = decodeJWT(token).toDomain()
            sub = accountInfo.sub
            Response.Success(accountInfo)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message ?: "Unknown error")
        }
    }

    private suspend fun decodeJWT(jwt: String): AccountInfoDto =
        withContext(Dispatchers.Default) {
            val parts = jwt.split(".")
            val accountInfoDto = Base64.decode(parts[1]).decodeToString()
            json.decodeFromString<AccountInfoDto>(accountInfoDto)
        }

    override suspend fun refreshAccount(): Response<AccountInfo> {
        return parseAccountInfo()
    }

    override suspend fun logOut() = tokenStorage.clearInfo()

    override val loggedIn: Flow<Boolean> = tokenStorage.loggedIn

    override fun getImageLink(): String = api.getImageLink(sub)

    override suspend fun sendImage(data: ByteArray): Response<Unit> =
        parseLockBoxApiResponse {
            api.sendImage(data = data)
        }

    override suspend fun getSessions(): Response<Sessions> =
        parseLockBoxApiResponse(
            apiCall = { api.getSessions() },
            mapper = SessionsDto::toDomain
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
            mapper = UpdateAccountDto::toDomain
        )

    override suspend fun deactivateUser(): Response<UpdateAccount> =
        parseLockBoxApiResponse(
            apiCall = {
                api.updateUserInfo(enabled = false)
            },
            mapper = UpdateAccountDto::toDomain
        )

    override suspend fun closeOtherSessions(): Response<CloseSessions> {
        val accountInfo = parseAccountInfo()
        return parseLockBoxApiResponse(
            apiCall = {
                api.closeOtherSessions(id = (accountInfo as? Response.Success<AccountInfo>)?.data?.sessionId.orEmpty())
            },
            mapper = CloseSessionsDto::toDomain
        )
    }

    override suspend fun getProfileInfo(): Response<ProfileInfo> {
        return parseLockBoxApiResponse(
            apiCall = {
                api.getProfileInfo()
            },
            mapper = ProfileInfoDto::toDomain
        )
    }

    override suspend fun updateProfileInfo(
        email: String?,
        phoneNumber: String?,
        fullName: String?,
        livingAddress: String?,
        passportIssuer: String?,
        passportNumber: String?,
        passportSeries: String?
    ): Response<Unit> {
        return parseLockBoxApiResponse {
            api.updateProfileInfo(
                email = email,
                phoneNumber = phoneNumber,
                fullName = fullName,
                livingAddress = livingAddress,
                passportIssuer = passportIssuer,
                passportNumber = passportNumber,
                passportSeries = passportSeries,
            )
        }
    }
}
