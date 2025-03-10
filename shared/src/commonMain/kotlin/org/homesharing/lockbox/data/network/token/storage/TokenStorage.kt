package org.homesharing.lockbox.data.network.token.storage

import kotlinx.coroutines.flow.Flow

internal const val ACCESS_TOKEN_NAME = "access_token"
internal const val REFRESH_TOKEN_NAME = "refresh_token"
internal const val LAST_REFRESH_TIME_NAME = "refresh_token"
internal const val TOKEN_STORAGE_NAME = "token_storage"

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class TokenStorage {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String

    suspend fun saveRefreshToken(token: String)
    suspend fun saveAccessToken(token: String)

    suspend fun getLastRefreshTime(): Long
    suspend fun saveLastRefreshTime(time: Long)

    suspend fun clearInfo()

    fun loggedIn(): Flow<Boolean>
}