package com.dreamsoftware.vaultkeeper.service.crypto

import android.util.Base64
import com.dreamsoftware.vaultkeeper.domain.service.ICryptoService
import java.security.NoSuchAlgorithmException
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.InvalidKeySpecException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

internal class CryptoServiceImpl : ICryptoService {

    private companion object {
        const val SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256"
        const val TRANSFORMATION = "AES/CFB/PKCS5Padding"
        const val ALGORITHM = "AES"
        const val ITERATION_COUNT = 10000 // Reduced iteration count for performance
        const val KEY_LENGTH = 256
        const val IV_SIZE = 16
    }

    private val charset by lazy {
        charset("UTF-8")
    }

    // Cache for derived keys to improve performance
    private val keyCache = mutableMapOf<Pair<String, String>, SecretKey>()

    /**
     * Encrypts and encodes the data as Base64.
     * @param password the password used to derive the key
     * @param salt the salt used in key derivation
     * @param data the data to be encrypted
     * @return the encrypted and encoded data as a Base64 string
     */
    override fun encryptAndEncode(password: String, salt: String, data: String): String =
        String(Base64.encode(encrypt(password, salt, data.toByteArray()), Base64.DEFAULT), charset)

    /**
     * Decodes the Base64 encoded data and decrypts it.
     * @param password the password used to derive the key
     * @param salt the salt used in key derivation
     * @param data the Base64 encoded data to be decrypted
     * @return the decrypted data as a string
     */
    override fun decodeAndDecrypt(password: String, salt: String, data: String): String =
        String(decrypt(password, salt, Base64.decode(data, Base64.DEFAULT)), charset)

    /**
     * Derives a secret key from the given password and salt.
     * @param password the password used to derive the key
     * @param salt the salt used in key derivation
     * @return the derived secret key
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun getKeyFromPassword(password: String, salt: String): SecretKey {
        val cacheKey = password to salt
        return keyCache[cacheKey] ?: run {
            val factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
            val spec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), ITERATION_COUNT, KEY_LENGTH)
            val secretKey = SecretKeySpec(factory.generateSecret(spec).encoded, ALGORITHM)
            keyCache[cacheKey] = secretKey
            secretKey
        }
    }

    /**
     * Encrypts the given data using the derived key and appends the IV.
     * @param password the password used to derive the key
     * @param salt the salt used in key derivation
     * @param data the data to be encrypted
     * @return the encrypted data with the IV prepended
     */
    private fun encrypt(password: String, salt: String, data: ByteArray): ByteArray {
        val cipher = getCipher(Cipher.ENCRYPT_MODE, password, salt)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(data)
        return iv + encrypted // Prepend IV to encrypted data
    }

    /**
     * Decrypts the given data using the derived key and the extracted IV.
     * @param password the password used to derive the key
     * @param salt the salt used in key derivation
     * @param data the data to be decrypted, with the IV prepended
     * @return the decrypted data
     */
    private fun decrypt(password: String, salt: String, data: ByteArray): ByteArray {
        val iv = data.sliceArray(0 until IV_SIZE)
        val encryptedData = data.sliceArray(IV_SIZE until data.size)
        val cipher = getCipher(Cipher.DECRYPT_MODE, password, salt, IvParameterSpec(iv))
        return cipher.doFinal(encryptedData)
    }

    /**
     * Initializes a cipher for encryption or decryption.
     * @param mode the mode of the cipher (Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE)
     * @param password the password used to derive the key
     * @param salt the salt used in key derivation
     * @param params the algorithm parameters (IV) used for cipher initialization
     * @return the initialized cipher
     */
    private fun getCipher(mode: Int, password: String, salt: String, params: AlgorithmParameterSpec? = null): Cipher =
        Cipher.getInstance(TRANSFORMATION).also {
            it.init(mode, getKeyFromPassword(password, salt), params)
        }
}