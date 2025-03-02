package org.homesharing.lockbox.domain.repository

import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.data.network.dto.AccountInfoDto
import org.homesharing.lockbox.data.network.dto.update_user.UpdateAccountResponseDto
import org.homesharing.lockbox.domain.model.Session

interface AccountRepository {
    suspend fun parseAccountInfo(): AccountInfoDto
    suspend fun logOut()
    fun loggedIn(): Flow<Boolean>
    suspend fun getImageLink(sub: String): String
    suspend fun sendImage(data: ByteArray)
    suspend fun getSessions(): List<Session>
    suspend fun updateInfo(
        name: String? = null,
        surname: String? = null,
        email: String? = null
    ): UpdateAccountResponseDto
    suspend fun deactivateUser(): UpdateAccountResponseDto
}
