package org.homesharing.lockbox.data.network.token.storage

internal const val ACCESS_TOKEN_NAME = "access_token"
internal const val REFRESH_TOKEN_NAME = "refresh_token"
internal const val TOKEN_STORAGE_NAME = "token_storage"

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class TokenStorage {
    fun getAccessToken(): String
    fun getRefreshToken(): String

    fun saveRefreshToken(token: String)
    fun saveAccessToken(token: String)

    fun clearInfo()

    fun canLogIn(): Boolean
}