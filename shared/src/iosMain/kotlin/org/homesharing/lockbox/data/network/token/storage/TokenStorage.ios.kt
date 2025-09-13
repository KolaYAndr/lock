@file:OptIn(ExperimentalForeignApi::class)

package org.homesharing.lockbox.data.network.token.storage

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFBooleanTrue
import platform.Foundation.NSData
import platform.Foundation.NSUserDefaults
import platform.Foundation.dataWithBytes
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.errSecSuccess
import platform.Security.kSecAttrAccount
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import platform.posix.memcpy

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TokenStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val loggedIn = MutableStateFlow(false)

    init {
        coroutineScope.launch {
            loggedIn.update {
                getAccessToken().isNotBlank() && getRefreshToken().isNotBlank()
            }
        }
    }

    actual suspend fun getAccessToken(): String {
        return getKeychainString(ACCESS_TOKEN_NAME) ?: ""
    }

    actual suspend fun getRefreshToken(): String {
        return getKeychainString(REFRESH_TOKEN_NAME) ?: ""
    }

    actual suspend fun saveTokens(accessToken: String, refreshToken: String) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
        loggedIn.update { true }
    }

    actual suspend fun clearInfo() {
        deleteKeychainString(ACCESS_TOKEN_NAME)
        deleteKeychainString(REFRESH_TOKEN_NAME)
        userDefaults.removeObjectForKey(REFRESH_IN_NAME)
        loggedIn.update { false }
    }

    actual fun loggedIn(): Flow<Boolean> = loggedIn.asStateFlow()
    actual suspend fun getNextRefreshTime(): Long {
        return userDefaults.integerForKey(REFRESH_IN_NAME)
    }

    actual suspend fun saveNextRefreshTime(time: Long) {
        userDefaults.setInteger(time, forKey = REFRESH_IN_NAME)
    }

    private fun saveAccessToken(token: String) {
        setKeychainString(value = token, key = ACCESS_TOKEN_NAME)
    }

    private fun saveRefreshToken(token: String) {
        setKeychainString(value = token, key = REFRESH_TOKEN_NAME)
    }

    private fun setKeychainString(key: String, value: String) {
        val data = value.toNSData()

        val query = mapOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key,
            kSecValueData to data
        ) as CFDictionaryRef

        SecItemAdd(query, null)
    }

    private fun getKeychainString(key: String): String? {
        val query = mapOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key,
            kSecReturnData to kCFBooleanTrue!!,
            kSecMatchLimit to kSecMatchLimitOne
        ) as CFDictionaryRef

        memScoped {
            val result = nativeHeap.alloc<CFTypeRefVar>()
            val status = SecItemCopyMatching(query, result.ptr)

            if (status == errSecSuccess) {
                val nsData = result.value as NSData
                return nsData.toByteArray().decodeToString()
            }
        }

        return null
    }

    private fun deleteKeychainString(key: String) {
        val query = mapOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key
        ) as CFDictionaryRef

        SecItemDelete(query)
    }

    private fun String.toNSData(): NSData {
        val bytes = this.encodeToByteArray()
        val data = bytes.usePinned { pinned ->
            NSData.dataWithBytes(pinned.addressOf(0), bytes.size.toULong())
        }
        return data
    }

    private fun NSData.toByteArray(): ByteArray {
        if (length.toInt() == 0) return ByteArray(0)

        return ByteArray(this.length.toInt()).apply {
            usePinned { pinned ->
                memcpy(pinned.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
            }
        }
    }
}
