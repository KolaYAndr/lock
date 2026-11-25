package org.homesharing.lockbox.data.network.token

import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermission
import java.security.KeyStore
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

internal class LockboxCipher {
    private companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val KEY_ALGORITHM = "AES"
        private const val KEYSTORE_TYPE = "JCEKS"
        private const val KEY_ALIAS = "org.homesharing.lockbox"
        private const val KEY_SIZE = 256
        private const val GCM_TAG_LENGTH = 128
        private const val KEYSTORE_FILE_NAME = "lockbox.jceks"
    }

    private val keyStoreFile: File by lazy {
        val appDataDir = File(System.getProperty("user.home"), ".lockbox")
        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
            restrictDirectoryToOwner(appDataDir)
        }
        File(appDataDir, KEYSTORE_FILE_NAME)
    }

    private val keyStorePassword: CharArray
        get() = "${System.getProperty("user.name").orEmpty()}@org.homesharing.lockbox".toCharArray()

    private val secretKey: SecretKey by lazy { getOrCreateSecretKey() }

    @Synchronized
    private fun getOrCreateSecretKey(): SecretKey {
        val password = keyStorePassword
        val keyStore = KeyStore.getInstance(KEYSTORE_TYPE)
        val protection = KeyStore.PasswordProtection(password)

        if (keyStoreFile.exists()) {
            keyStoreFile.inputStream().use { keyStore.load(it, password) }
            val entry = keyStore.getEntry(KEY_ALIAS, protection) as? KeyStore.SecretKeyEntry
            if (entry != null) return entry.secretKey
        } else {
            keyStore.load(null, password)
        }

        val key = KeyGenerator.getInstance(KEY_ALGORITHM).apply { init(KEY_SIZE) }.generateKey()
        keyStore.setEntry(KEY_ALIAS, KeyStore.SecretKeyEntry(key), protection)
        keyStoreFile.outputStream().use { keyStore.store(it, password) }
        restrictToOwner(keyStoreFile)
        return key
    }

    private fun restrictToOwner(file: File) {
        try {
            Files.setPosixFilePermissions(
                file.toPath(),
                setOf(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE)
            )
        } catch (_: UnsupportedOperationException) {
            file.setReadable(false, false)
            file.setReadable(true, true)
            file.setWritable(false, false)
            file.setWritable(true, true)
        }
    }

    private fun restrictDirectoryToOwner(dir: File) {
        try {
            Files.setPosixFilePermissions(
                dir.toPath(),
                setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE,
                )
            )
        } catch (_: UnsupportedOperationException) {
            dir.setReadable(false, false)
            dir.setReadable(true, true)
            dir.setWritable(false, false)
            dir.setWritable(true, true)
            dir.setExecutable(false, false)
            dir.setExecutable(true, true)
        }
    }

    fun encrypt(plainText: String): Pair<ByteArray, ByteArray> {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return Pair(cipher.iv, cipher.doFinal(plainText.toByteArray(Charsets.UTF_8)))
    }

    fun decrypt(iv: ByteArray, cipherText: ByteArray): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return String(cipher.doFinal(cipherText), Charsets.UTF_8)
    }

    fun toBase64(bytes: ByteArray): String = Base64.getEncoder().encodeToString(bytes)
    fun fromBase64(str: String): ByteArray = Base64.getDecoder().decode(str)
}
