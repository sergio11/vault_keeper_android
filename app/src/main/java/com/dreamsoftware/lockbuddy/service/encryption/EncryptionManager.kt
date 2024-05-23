package com.dreamsoftware.lockbuddy.service.encryption

import android.content.Context
import android.util.Base64
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.qualifiers.ApplicationContext
import com.dreamsoftware.lockbuddy.signin.GoogleAuthUiClient
import com.dreamsoftware.lockbuddy.util.BYTE_SIZE
import com.dreamsoftware.lockbuddy.util.TRANSFORMATION
import com.dreamsoftware.lockbuddy.util.generateKey
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class EncryptionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    private val user = googleAuthUiClient.getSignedInUser()

    fun encrypt(input: String): String {
        if (input.isBlank()) {
            return input
        }
        return runCatching {
            val key = user?.email?.let { generateKey(user.userId, it) }
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val ivBytes = ByteArray(BYTE_SIZE)
            val ivSpec = IvParameterSpec(ivBytes)
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)
            val encryptedBytes = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
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
            val key = user?.email?.let { generateKey(user.userId, it) }
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val ivBytes = ByteArray(BYTE_SIZE)
            val ivSpec = IvParameterSpec(ivBytes)
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
            val encryptedBytes = Base64.decode(input, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            return String(decryptedBytes, Charsets.UTF_8)
        }.getOrElse {
            it.printStackTrace()
            ""
        }
    }

}