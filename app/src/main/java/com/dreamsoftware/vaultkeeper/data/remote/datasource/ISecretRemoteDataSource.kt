package com.dreamsoftware.vaultkeeper.data.remote.datasource

import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveSecretException
import com.dreamsoftware.vaultkeeper.data.remote.exception.SecretNotFoundException

interface ISecretRemoteDataSource {

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