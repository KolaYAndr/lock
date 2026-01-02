package org.homesharing.lockbox.data.network.token.storage

import kotlinx.coroutines.flow.Flow

internal const val ACCESS_TOKEN_NAME = "access_token"
internal const val REFRESH_TOKEN_NAME = "refresh_token"
internal const val REFRESH_IN_NAME = "refresh_in"
internal const val TOKEN_STORAGE_NAME = "token_storage"

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class TokenStorage {
    val loggedIn: Flow<Boolean>
    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun getNextRefreshTime(): Long

    suspend fun saveNextRefreshTime(time: Long)

    suspend fun clearInfo()
}
