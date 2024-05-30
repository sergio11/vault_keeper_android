package com.dreamsoftware.vaultkeeper.domain.service

interface ICryptoService {
    fun encryptAndEncode(password: String, salt: String, data: String): String

    fun decodeAndDecrypt(password: String, salt: String, data: String): String
}