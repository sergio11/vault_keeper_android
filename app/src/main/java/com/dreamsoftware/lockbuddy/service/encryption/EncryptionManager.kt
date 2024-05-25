package com.dreamsoftware.lockbuddy.service.encryption

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EncryptionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun encrypt(input: String): String {
        if (input.isBlank()) {
            return input
        }
        return runCatching {
            /*//val key = user?.email?.let { generateKey(user.userId, it) }
            val key = ""
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val ivBytes = ByteArray(BYTE_SIZE)
            val ivSpec = IvParameterSpec(ivBytes)
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)
            val encryptedBytes = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)*/
            return ""
        }.getOrElse {
            it.printStackTrace()
            ""
        }
    }

    fun decrypt(input: String): String {
        if (input.isBlank()) {
            return input
        }
        return runCatching {
            /*val key = user?.email?.let { generateKey(user.userId, it) }
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val ivBytes = ByteArray(BYTE_SIZE)
            val ivSpec = IvParameterSpec(ivBytes)
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
            val encryptedBytes = Base64.decode(input, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            return String(decryptedBytes, Charsets.UTF_8)*/
            return ""
        }.getOrElse {
            it.printStackTrace()
            ""
        }
    }

}