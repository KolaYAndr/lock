package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.data.network.model.AccountInfo

interface AccountRepository {
    suspend fun parseAccountInfo(): AccountInfo
    suspend fun logOut()
    suspend fun canLogin(): Boolean
    suspend fun getAvatarLink(sub: String): String
    suspend fun sendImage(data: ByteArray)
}
