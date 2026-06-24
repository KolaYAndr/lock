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
        return keychain.get(ACCESS_TOKEN_NAME).orEmpty()
    }

    override suspend fun getRefreshToken(): String {
        return keychain.get(REFRESH_TOKEN_NAME).orEmpty()
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
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
