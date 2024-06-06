package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.GetSecretException
import com.dreamsoftware.vaultkeeper.domain.exception.SaveSecretException
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.model.SaveMasterKeyBO

interface ISecretRepository {

    @Throws(SaveSecretException::class)
    suspend fun save(secret: SaveMasterKeyBO): PBEDataBO

    @Throws(GetSecretException::class)
    suspend fun getSecretForUser(userUid: String): PBEDataBO

    @Throws(GetSecretException::class)
    suspend fun hasSecret(userUid: String): Boolean
}