package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.data.network.dto.AccountInfoDto
import org.homesharing.lockbox.domain.repository.AccountRepository
import io.ktor.util.decodeBase64String
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.data.network.dto.update_user.UpdateAccountRequestDto
import org.homesharing.lockbox.data.network.dto.update_user.UpdateAccountResponseDto
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.model.Session
import org.homesharing.lockbox.domain.model.toDomain

class AccountRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage
) : AccountRepository {

    override suspend fun parseAccountInfo(): AccountInfoDto =
        withContext(Dispatchers.IO) {
            val token = api.refreshTokens(tokenStorage.getRefreshToken())
            with(tokenStorage) {
                saveAccessToken(token.accessToken)
                saveRefreshToken(token.refreshToken)
            }
            decodeJWT(token.accessToken)
        }

    private suspend fun decodeJWT(jwt: String): AccountInfoDto = withContext(Dispatchers.Default) {
        val parts = jwt.split(".")
        val accountInfoJson = parts[1].decodeBase64String()

        Json.decodeFromString<AccountInfoDto>(accountInfoJson)
    }

    override suspend fun logOut() = withContext(Dispatchers.IO) {
        tokenStorage.clearInfo()
    }

    override fun loggedIn(): Flow<Boolean> = tokenStorage.loggedIn()

    override suspend fun getImageLink(sub: String): String = withContext(Dispatchers.IO) {
        api.getImageLinkBySub(sub)
    }

    override suspend fun sendImage(data: ByteArray) = withContext(Dispatchers.IO) {
        api.sendImage(data)
    }

    override suspend fun getSessions(): List<Session> = withContext(Dispatchers.IO) {
        api.getSessions().body.sessions.map { it.toDomain() }
    }

    override suspend fun updateInfo(
        name: String?,
        surname: String?,
        email: String?
    ): UpdateAccountResponseDto = withContext(Dispatchers.IO) {
        val request = UpdateAccountRequestDto(email = email, firstName = name, lastName = surname)
        api.updateUserInfo(request)
    }

    override suspend fun deactivateUser(): UpdateAccountResponseDto = withContext(Dispatchers.IO) {
        val deactivateRequest = UpdateAccountRequestDto(enabled = false)
        api.updateUserInfo(deactivateRequest)
    }
}
