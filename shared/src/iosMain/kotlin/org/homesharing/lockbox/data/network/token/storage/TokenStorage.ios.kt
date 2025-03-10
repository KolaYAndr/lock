package org.homesharing.lockbox.data.network.token.storage

import kotlinx.coroutines.flow.Flow
import platform.Foundation.NSUserDefaults

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TokenStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual suspend fun getAccessToken(): String {
        return userDefaults.stringForKey(ACCESS_TOKEN_NAME)!!
    }

    actual suspend fun saveAccessToken(token: String) {
        userDefaults.setObject(token, forKey = ACCESS_TOKEN_NAME)
    }

    actual suspend fun getRefreshToken(): String {
        return userDefaults.stringForKey(REFRESH_TOKEN_NAME)!!
    }

    actual suspend fun saveRefreshToken(token: String) {
        userDefaults.setObject(token, forKey = REFRESH_TOKEN_NAME)
    }

    actual suspend fun clearInfo() {
        userDefaults.removeObjectForKey(ACCESS_TOKEN_NAME)
        userDefaults.removeObjectForKey(REFRESH_TOKEN_NAME)
    }

//    actual suspend fun loggedIn(): Boolean = userDefaults.stringForKey(ACCESS_TOKEN_NAME) != null
    actual fun loggedIn(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    actual suspend fun getLastRefreshTime(): Long {
        return userDefaults.integerForKey(LAST_REFRESH_TIME_NAME)
    }

    actual suspend fun saveLastRefreshTime(time: Long) {
        userDefaults.setInteger(time, forKey = LAST_REFRESH_TIME_NAME)
    }
}
