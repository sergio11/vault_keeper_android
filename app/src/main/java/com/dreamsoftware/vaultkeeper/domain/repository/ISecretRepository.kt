package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.NotSecretFoundException
import com.dreamsoftware.vaultkeeper.domain.exception.SaveSecretDetailsException
import com.dreamsoftware.vaultkeeper.domain.exception.ValidateSecretException
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.model.SaveSecretBO
import com.dreamsoftware.vaultkeeper.domain.model.ValidateSecretBO

interface ISecretRepository {

    @Throws(SaveSecretDetailsException::class)
    suspend fun save(secret: SaveSecretBO): PBEDataBO

    @Throws(NotSecretFoundException::class)
    suspend fun deleteUserSecret(userUid: String)

    @Throws(NotSecretFoundException::class)
    suspend fun getSecretForUser(userUid: String): PBEDataBO

    @Throws(ValidateSecretException::class)
    suspend fun validateSecretForUser(secret: ValidateSecretBO): PBEDataBO

    @Throws(NotSecretFoundException::class)
    suspend fun hasSecret(userUid: String): Boolean
}