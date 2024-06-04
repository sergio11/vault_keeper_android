package com.dreamsoftware.vaultkeeper.data.remote.datasource

import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveSecureCardException

interface ISecureCardsRemoteDataSource {

    @Throws(SaveSecureCardException::class)
    suspend fun save(secureCard: SecureCardDTO)
}