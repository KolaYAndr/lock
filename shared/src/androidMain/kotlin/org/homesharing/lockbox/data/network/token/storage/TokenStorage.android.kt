package org.homesharing.lockbox.data.network.token.storage

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = TOKEN_STORAGE_NAME,
    produceMigrations = ::sharedPreferencesMigration
)

private fun sharedPreferencesMigration(context: Context) =
    listOf(SharedPreferencesMigration(context, TOKEN_STORAGE_NAME))

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TokenStorage(context: Context) {
    private val dataStore = context.dataStore

    actual suspend fun getAccessToken(): String {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY] ?: ""
        }.first()
    }

    actual suspend fun getRefreshToken(): String {
        return dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY] ?: ""
        }.first()
    }

    actual suspend fun saveRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }

    actual suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    actual suspend fun clearInfo() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    actual fun loggedIn(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences.contains(ACCESS_TOKEN_KEY)
    }

    actual suspend fun getLastRefreshTime(): Long {
        return dataStore.data.map { preferences ->
            preferences[LAST_REFRESH_KEY] ?: 0
        }.first()
    }


    actual suspend fun saveLastRefreshTime(time: Long) {
        dataStore.edit { preferences ->
            preferences[LAST_REFRESH_KEY] = time
        }
    }

    private companion object {
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(REFRESH_TOKEN_NAME)
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(ACCESS_TOKEN_NAME)
        private val LAST_REFRESH_KEY = longPreferencesKey(LAST_REFRESH_TIME_NAME)
    }
}
