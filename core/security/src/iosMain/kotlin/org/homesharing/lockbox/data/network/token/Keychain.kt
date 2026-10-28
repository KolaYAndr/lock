package org.homesharing.lockbox.data.network.token

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import platform.CoreFoundation.CFDataRef
import platform.CoreFoundation.CFDictionaryCreate
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.CFTypeRef
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFBooleanTrue
import platform.CoreFoundation.kCFTypeDictionaryKeyCallBacks
import platform.CoreFoundation.kCFTypeDictionaryValueCallBacks
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.create
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.errSecSuccess
import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class, BetaInteropApi::class)
internal class Keychain(private val service: String) {

    fun set(key: String, value: String) {
        platform.Foundation.NSLog("Keychain: set key=$key, valueLength=${value.length}")
        delete(key)

        val account = cfString(key)
        val svc = cfString(service)
        val data = cfData(value)
        val attributes = cfDictionaryOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrService to svc,
            kSecAttrAccount to account,
            kSecValueData to data,
            kSecAttrAccessible to kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly,
        )
        SecItemAdd(attributes, null)

        CFRelease(attributes)
        account?.let { CFRelease(it) }
        svc?.let { CFRelease(it) }
        data?.let { CFRelease(it) }
    }

    fun get(key: String): String? {
        val account = cfString(key)
        val svc = cfString(service)
        val query = cfDictionaryOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrService to svc,
            kSecAttrAccount to account,
            kSecReturnData to kCFBooleanTrue,
            kSecMatchLimit to kSecMatchLimitOne,
        )

        val result: CFTypeRef? = memScoped {
            val out = alloc<CFTypeRefVar>()
            val status = SecItemCopyMatching(query, out.ptr)
            if (status == errSecSuccess) {
                out.value
            } else {
                platform.Foundation.NSLog("Keychain: get key=$key failed with status=$status")
                null
            }
        }

        CFRelease(query)
        account?.let { CFRelease(it) }
        svc?.let { CFRelease(it) }

        if (result == null) return null
        val nsData = CFBridgingRelease(result) as? NSData ?: return null
        val stringValue = nsData.toUtf8String()
        platform.Foundation.NSLog("Keychain: get key=$key, valueLength=${stringValue.length}")
        return stringValue
    }

    fun delete(key: String) {
        val account = cfString(key)
        val svc = cfString(service)
        val query = cfDictionaryOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrService to svc,
            kSecAttrAccount to account,
        )
        SecItemDelete(query)

        CFRelease(query)
        account?.let { CFRelease(it) }
        svc?.let { CFRelease(it) }
    }

    private fun cfString(value: String): CFStringRef? =
        CFBridgingRetain(value)?.reinterpret()

    private fun cfData(value: String): CFDataRef? {
        val bytes = value.encodeToByteArray()
        val nsData = if (bytes.isEmpty()) {
            NSData()
        } else {
            bytes.usePinned { pinned ->
                NSData.create(bytes = pinned.addressOf(0), length = bytes.size.convert())
            }
        }
        return CFBridgingRetain(nsData)?.reinterpret()
    }

    private fun NSData.toUtf8String(): String {
        val length = this.length.toInt()
        if (length == 0) return ""
        val bytes = ByteArray(length)
        bytes.usePinned { pinned ->
            memcpy(pinned.addressOf(0), this.bytes, this.length)
        }
        return bytes.decodeToString()
    }

    private fun cfDictionaryOf(vararg pairs: Pair<CFStringRef?, CFTypeRef?>): CFDictionaryRef =
        memScoped {
            CFDictionaryCreate(
                allocator = kCFAllocatorDefault,
                keys = allocArrayOf(pairs.map { it.first }).reinterpret(),
                values = allocArrayOf(pairs.map { it.second }).reinterpret(),
                numValues = pairs.size.convert(),
                keyCallBacks = kCFTypeDictionaryKeyCallBacks.ptr,
                valueCallBacks = kCFTypeDictionaryValueCallBacks.ptr,
            )!!
        }
}
