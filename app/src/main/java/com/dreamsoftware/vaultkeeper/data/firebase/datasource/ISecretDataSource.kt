package com.dreamsoftware.vaultkeeper.data.firebase.datasource

import com.dreamsoftware.vaultkeeper.data.firebase.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.data.firebase.exception.SaveSecretException
import com.dreamsoftware.vaultkeeper.data.firebase.exception.SecretNotFoundException

interface ISecretDataSource {

    /**
     * @param secret
     */
    @Throws(SaveSecretException::class)
    suspend fun save(secret: SecretDTO)

    /**
     * @param uid
     */
    @Throws(SecretNotFoundException::class)
    suspend fun getByUserUid(uid: String): SecretDTO
}