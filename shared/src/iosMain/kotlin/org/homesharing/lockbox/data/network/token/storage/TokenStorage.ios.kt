package org.homesharing.lockbox.data.network.token.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import platform.Foundation.NSUserDefaults

internal class TokenStorageImpl(
    private val userDefaults: NSUserDefaults
) : TokenStorage {
    private val _loggedIn = MutableStateFlow(
        userDefaults.stringForKey(ACCESS_TOKEN_NAME)?.isNotBlank() == true
    )

    override val loggedIn: Flow<Boolean> = _loggedIn

    override suspend fun getAccessToken(): String {
        return userDefaults.stringForKey(ACCESS_TOKEN_NAME).orEmpty()
    }

    override suspend fun getRefreshToken(): String {
        return userDefaults.stringForKey(REFRESH_TOKEN_NAME).orEmpty()
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
        _loggedIn.update { true }
    }

    override suspend fun clearInfo() {
        userDefaults.removeObjectForKey(ACCESS_TOKEN_NAME)
        userDefaults.removeObjectForKey(REFRESH_TOKEN_NAME)
        userDefaults.removeObjectForKey(REFRESH_IN_NAME)
        _loggedIn.update { false }
    }

    override suspend fun getNextRefreshTime(): Long {
        return userDefaults.integerForKey(REFRESH_IN_NAME)
    }

    override suspend fun saveNextRefreshTime(time: Long) {
        userDefaults.setInteger(time, forKey = REFRESH_IN_NAME)
    }

    private fun saveAccessToken(token: String) {
        userDefaults.setObject(token, forKey = ACCESS_TOKEN_NAME)
    }

    private fun saveRefreshToken(token: String) {
        userDefaults.setObject(token, forKey = REFRESH_TOKEN_NAME)
    }
}
