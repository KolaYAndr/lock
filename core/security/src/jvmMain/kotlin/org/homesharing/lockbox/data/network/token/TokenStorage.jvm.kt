package org.homesharing.lockbox.data.network.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.Path.Companion.toPath
import java.io.File

internal class TokenStorageImpl(
    private val cipher: LockboxCipher
) : TokenStorage {
    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            val userHome = System.getProperty("user.home")
            val appDataDir = File(userHome, ".lockbox")
            if (!appDataDir.exists()) appDataDir.mkdirs()
            File(appDataDir, "token_storage.preferences_pb").absolutePath.toPath()
        }
    )

    override val loggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences.contains(ACCESS_TOKEN_KEY) && preferences.contains(REFRESH_TOKEN_KEY)
    }

    override suspend fun getAccessToken(): String =
        decryptToken(ACCESS_TOKEN_KEY, ACCESS_TOKEN_IV_KEY)

    override suspend fun getRefreshToken(): String =
        decryptToken(REFRESH_TOKEN_KEY, REFRESH_TOKEN_IV_KEY)

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        withContext(Dispatchers.IO) {
            val (accessIv, encryptedAccess) = cipher.encrypt(accessToken)
            val (refreshIv, encryptedRefresh) = cipher.encrypt(refreshToken)
            dataStore.edit { prefs ->
                prefs[ACCESS_TOKEN_IV_KEY] = cipher.toBase64(accessIv)
                prefs[ACCESS_TOKEN_KEY] = cipher.toBase64(encryptedAccess)
                prefs[REFRESH_TOKEN_IV_KEY] = cipher.toBase64(refreshIv)
                prefs[REFRESH_TOKEN_KEY] = cipher.toBase64(encryptedRefresh)
            }
        }
    }

    override suspend fun clearInfo() {
        dataStore.edit { it.clear() }
    }

    private suspend fun decryptToken(
        tokenKey: Preferences.Key<String>,
        ivKey: Preferences.Key<String>,
    ): String = withContext(Dispatchers.IO) {
        val prefs = dataStore.data.first()
        val tokenData = prefs[tokenKey]
        val iv = prefs[ivKey]

        if (tokenData == null || iv == null) return@withContext ""

        return@withContext try {
            cipher.decrypt(cipher.fromBase64(iv), cipher.fromBase64(tokenData))
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            // Если расшифровка не удалась (например, ключ инвалидирован), лучше очистить данные
            clearInfo()
            ""
        }
    }

    private companion object {
        private const val ACCESS_TOKEN_IV = "access_token_iv"
        private const val REFRESH_TOKEN_IV = "refresh_token_iv"

        private val ACCESS_TOKEN_KEY = stringPreferencesKey(ACCESS_TOKEN_NAME)
        private val ACCESS_TOKEN_IV_KEY = stringPreferencesKey(ACCESS_TOKEN_IV)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(REFRESH_TOKEN_NAME)
        private val REFRESH_TOKEN_IV_KEY = stringPreferencesKey(REFRESH_TOKEN_IV)
    }
}
