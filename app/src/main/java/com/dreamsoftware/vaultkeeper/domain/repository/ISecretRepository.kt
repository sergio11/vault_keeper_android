package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.GenerateSecretException
import com.dreamsoftware.vaultkeeper.domain.exception.GetSecretException
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO

interface ISecretRepository {

    @Throws(GenerateSecretException::class)
    suspend fun generate(userUid: String): PBEDataBO

    @Throws(GetSecretException::class)
    suspend fun getSecretForUser(userUid: String): PBEDataBO
}