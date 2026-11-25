package org.homesharing.lockbox.data.network.token

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class TokenStorageImpl(
    private val keychain: Keychain
) : TokenStorage {
    private val mutex = Mutex()
    private val _loggedIn = MutableStateFlow(
        keychain.get(ACCESS_TOKEN_NAME)?.isNotBlank() == true &&
            keychain.get(REFRESH_TOKEN_NAME)?.isNotBlank() == true
    )

    override val loggedIn: Flow<Boolean> = _loggedIn

    override suspend fun getAccessToken(): String = mutex.withLock {
        keychain.get(ACCESS_TOKEN_NAME).orEmpty()
    }

    override suspend fun getRefreshToken(): String = mutex.withLock {
        keychain.get(REFRESH_TOKEN_NAME).orEmpty()
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String): Unit = mutex.withLock {
        val accessSaved = keychain.set(ACCESS_TOKEN_NAME, accessToken)
        val refreshSaved = keychain.set(REFRESH_TOKEN_NAME, refreshToken)
        if (accessSaved && refreshSaved) {
            _loggedIn.update { true }
        } else {
            keychain.delete(ACCESS_TOKEN_NAME)
            keychain.delete(REFRESH_TOKEN_NAME)
            _loggedIn.update { false }
        }
    }

    override suspend fun clearInfo(): Unit = mutex.withLock {
        keychain.delete(ACCESS_TOKEN_NAME)
        keychain.delete(REFRESH_TOKEN_NAME)
        keychain.delete(REFRESH_IN_NAME)
        _loggedIn.update { false }
    }
}
