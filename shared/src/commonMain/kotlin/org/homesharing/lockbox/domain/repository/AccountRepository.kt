package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.model.AccountInfo

interface AccountRepository {
    suspend fun parseAccountInfo(): AccountInfo
    suspend fun logOut()
    suspend fun canLogin(): Boolean
}
