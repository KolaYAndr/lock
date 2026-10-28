package org.homesharing.lockbox.data.network.token

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class TokenStorageImpl(
    private val keychain: Keychain
) : TokenStorage {
    private val _loggedIn = MutableStateFlow(
        keychain.get(ACCESS_TOKEN_NAME)?.isNotBlank() == true
    )

    override val loggedIn: Flow<Boolean> = _loggedIn

    override suspend fun getAccessToken(): String {
        val token = keychain.get(ACCESS_TOKEN_NAME).orEmpty()
        platform.Foundation.NSLog("TokenStorage: getAccessToken, length=${token.length}")
        return token
    }

    override suspend fun getRefreshToken(): String {
        val token = keychain.get(REFRESH_TOKEN_NAME).orEmpty()
        platform.Foundation.NSLog("TokenStorage: getRefreshToken, length=${token.length}")
        return token
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        platform.Foundation.NSLog("TokenStorage: saveTokens, accessLength=${accessToken.length}, refreshLength=${refreshToken.length}")
        keychain.set(ACCESS_TOKEN_NAME, accessToken)
        keychain.set(REFRESH_TOKEN_NAME, refreshToken)
        _loggedIn.update { true }
    }

    override suspend fun clearInfo() {
        keychain.delete(ACCESS_TOKEN_NAME)
        keychain.delete(REFRESH_TOKEN_NAME)
        keychain.delete(REFRESH_IN_NAME)
        _loggedIn.update { false }
    }
}
