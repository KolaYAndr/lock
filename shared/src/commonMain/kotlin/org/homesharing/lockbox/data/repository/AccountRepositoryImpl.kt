package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.model.AccountInfo
import org.homesharing.lockbox.domain.repository.AccountRepository
import io.ktor.util.decodeBase64String

class AccountRepositoryImpl(private val tokenStorage: TokenStorage): AccountRepository {
    override suspend fun parseAccountInfo(): AccountInfo =
        withContext(Dispatchers.Default) {
            val token = async(Dispatchers.IO) { tokenStorage.getAccessToken() }
            decodeJWT(token.await()!!)
        }

    private fun decodeJWT(jwt: String): AccountInfo {
        val parts = jwt.split(".")
        val payload = parts[1].decodeBase64String()

        return Json.decodeFromString<AccountInfo>(payload)
    }

    override suspend fun logOut() = withContext(Dispatchers.IO) {
        tokenStorage.clearInfo()
    }

    override suspend fun canLogin(): Boolean = withContext(Dispatchers.IO) {
        tokenStorage.canLogIn()
    }
}