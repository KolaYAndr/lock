package org.homesharing.lockbox.domain.repository

import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.account_info.AccountInfo
import org.homesharing.lockbox.domain.model.sessions.Session
import org.homesharing.lockbox.domain.model.update_account.UpdateAccount

interface AccountRepository {

    suspend fun parseAccountInfo(): Response<AccountInfo>

    suspend fun refreshAccount(): Response<AccountInfo>

    suspend fun logOut()

    fun loggedIn(): Flow<Boolean>

    suspend fun getImageLink(sub: String): String

    suspend fun sendImage(data: ByteArray)

    suspend fun getSessions(): Response<List<Session>>

    suspend fun updateInfo(
        name: String? = null,
        surname: String? = null,
        email: String? = null
    ): Response<UpdateAccount>

    suspend fun deactivateUser(): Response<UpdateAccount>

    suspend fun closeOtherSessions(currentSessionId: String): Response<String>
}
