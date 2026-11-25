package org.homesharing.lockbox.data.network.token

import kotlinx.coroutines.flow.Flow

const val ACCESS_TOKEN_NAME = "access_token"
const val REFRESH_TOKEN_NAME = "refresh_token"
const val REFRESH_IN_NAME = "refresh_in"
const val TOKEN_STORAGE_NAME = "token_storage"

interface TokenStorage {
    val loggedIn: Flow<Boolean>
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearInfo()
}
