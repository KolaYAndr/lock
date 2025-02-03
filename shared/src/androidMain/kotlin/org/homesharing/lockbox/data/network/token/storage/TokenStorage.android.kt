package org.homesharing.lockbox.data.network.token.storage

import android.content.Context

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TokenStorage(context: Context) {
    private val prefs = context.getSharedPreferences(TOKEN_STORAGE_NAME, Context.MODE_PRIVATE)

    actual fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_NAME, "")
    }

    actual fun getRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN_NAME, "")
    }

    actual fun saveRefreshToken(token: String) {
        prefs.edit().putString(REFRESH_TOKEN_NAME, token).apply()
    }

    actual fun saveAccessToken(token: String) {
        prefs.edit().putString(ACCESS_TOKEN_NAME, token).apply()
    }

    actual fun clearInfo() {
        prefs.edit().remove(ACCESS_TOKEN_NAME).apply()
        prefs.edit().remove(REFRESH_TOKEN_NAME).apply()
    }
}
