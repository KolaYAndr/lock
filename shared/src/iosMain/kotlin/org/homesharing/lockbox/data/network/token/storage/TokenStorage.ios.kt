package org.homesharing.lockbox.data.network.token.storage

import platform.Foundation.NSUserDefaults

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TokenStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual fun getAccessToken(): String? {
        return userDefaults.stringForKey(ACCESS_TOKEN_NAME)
    }

    actual fun saveAccessToken(token: String) {
        userDefaults.setObject(token, forKey = ACCESS_TOKEN_NAME)
    }

    actual fun getRefreshToken(): String? {
        return userDefaults.stringForKey(REFRESH_TOKEN_NAME)
    }

    actual fun saveRefreshToken(token: String) {
        userDefaults.setObject(token, forKey = REFRESH_TOKEN_NAME)
    }

    actual fun clearInfo() {
        userDefaults.removeObjectForKey(ACCESS_TOKEN_NAME)
        userDefaults.removeObjectForKey(REFRESH_TOKEN_NAME)
    }

    actual fun canLogIn(): Boolean = userDefaults.stringForKey(ACCESS_TOKEN_NAME) != null
}
