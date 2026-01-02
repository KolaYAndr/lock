package org.homesharing.lockbox.data.network.token.storage

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val Context.dataStore by preferencesDataStore(
    name = TOKEN_STORAGE_NAME,
    produceMigrations = ::sharedPreferencesMigration
)

private fun sharedPreferencesMigration(context: Context) =
    listOf(SharedPreferencesMigration(context, TOKEN_STORAGE_NAME))

private const val ACCESS_TOKEN_IV = "access_token_iv"
private const val REFRESH_TOKEN_IV = "refresh_token_iv"

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class TokenStorage(
    context: Context
) {
    private val dataStore = context.dataStore

    actual val loggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences.contains(ACCESS_TOKEN_KEY) && preferences.contains(REFRESH_TOKEN_KEY)
    }

    actual suspend fun getAccessToken(): String = withContext(Dispatchers.IO) {
        val prefs = dataStore.data.first()
        val accessTokenData = prefs[ACCESS_TOKEN_KEY]
        val accessIv = prefs[ACCESS_TOKEN_IV_KEY]

        if (accessTokenData == null || accessIv == null) return@withContext ""

        return@withContext try {
            val decodedIv = LockboxCipher.fromBase64(accessIv)
            val decodedData = LockboxCipher.fromBase64(accessTokenData)
            LockboxCipher.decrypt(decodedIv, decodedData)
        } catch (_: Throwable) {
            ""
        }
    }

    actual suspend fun getRefreshToken(): String = withContext(Dispatchers.IO) {
        val prefs = dataStore.data.first()
        val refreshTokenData = prefs[REFRESH_TOKEN_KEY]
        val refreshIv = prefs[REFRESH_TOKEN_IV_KEY]

        if (refreshTokenData == null || refreshIv == null) return@withContext ""

        return@withContext try {
            val decodedIv = LockboxCipher.fromBase64(refreshIv)
            val decodedData = LockboxCipher.fromBase64(refreshTokenData)
            LockboxCipher.decrypt(decodedIv, decodedData)
        } catch (_: Throwable) {
            ""
        }
    }

    actual suspend fun saveTokens(accessToken: String, refreshToken: String) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
    }

    actual suspend fun clearInfo() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    actual suspend fun getNextRefreshTime(): Long = dataStore.data.map { preferences ->
        preferences[REFRESH_IN_KEY] ?: 0
    }.first()


    actual suspend fun saveNextRefreshTime(time: Long) {
        dataStore.edit { preferences ->
            preferences[REFRESH_IN_KEY] = time
        }
    }

    private suspend fun saveRefreshToken(token: String) = withContext(Dispatchers.IO) {
        val (iv, cipher) = LockboxCipher.encrypt(token)
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN_IV_KEY] = LockboxCipher.toBase64(iv)
            prefs[REFRESH_TOKEN_KEY] = LockboxCipher.toBase64(cipher)
        }
    }

    private suspend fun saveAccessToken(token: String) = withContext(Dispatchers.IO) {
        val (iv, cipher) = LockboxCipher.encrypt(token)
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_IV_KEY] = LockboxCipher.toBase64(iv)
            prefs[ACCESS_TOKEN_KEY] = LockboxCipher.toBase64(cipher)
        }
    }

    private companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(ACCESS_TOKEN_NAME)
        private val ACCESS_TOKEN_IV_KEY = stringPreferencesKey(ACCESS_TOKEN_IV)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(REFRESH_TOKEN_NAME)
        private val REFRESH_TOKEN_IV_KEY = stringPreferencesKey(REFRESH_TOKEN_IV)
        private val REFRESH_IN_KEY = longPreferencesKey(REFRESH_IN_NAME)
    }
}
