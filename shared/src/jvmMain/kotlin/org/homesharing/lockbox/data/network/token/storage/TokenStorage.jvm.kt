package org.homesharing.lockbox.data.network.token.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath
import java.io.File

internal class TokenStorageImpl : TokenStorage {
    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            val userHome = System.getProperty("user.home")
            val appDataDir = File(userHome, ".lockbox")
            if (!appDataDir.exists()) appDataDir.mkdirs()
            File(appDataDir, "token_storage.preferences_pb").absolutePath.toPath()
        }
    )

    override val loggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN_KEY]?.isNotBlank() == true
    }

    override suspend fun getAccessToken(): String {
        return dataStore.data.map { it[ACCESS_TOKEN_KEY] }.first().orEmpty()
    }

    override suspend fun getRefreshToken(): String {
        return dataStore.data.map { it[REFRESH_TOKEN_KEY] }.first().orEmpty()
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = accessToken
            prefs[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    override suspend fun clearInfo() {
        dataStore.edit { it.clear() }
    }

    override suspend fun getNextRefreshTime(): Long {
        return dataStore.data.map { it[REFRESH_IN_KEY] }.first() ?: 0L
    }

    override suspend fun saveNextRefreshTime(time: Long) {
        dataStore.edit { it[REFRESH_IN_KEY] = time }
    }

    private companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(ACCESS_TOKEN_NAME)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(REFRESH_TOKEN_NAME)
        private val REFRESH_IN_KEY = longPreferencesKey(REFRESH_IN_NAME)
    }
}
