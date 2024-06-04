package com.dreamsoftware.vaultkeeper.data.remote.datasource

import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import com.dreamsoftware.vaultkeeper.data.remote.exception.DeleteSecureCardException
import com.dreamsoftware.vaultkeeper.data.remote.exception.FetchSecureCardException
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveSecureCardException

interface ISecureCardsRemoteDataSource {

    @Throws(SaveSecureCardException::class)
    suspend fun save(secureCard: SecureCardDTO)

    @Throws(FetchSecureCardException::class)
    suspend fun getAllByUserUid(userUid: String): List<SecureCardDTO>

    @Throws(DeleteSecureCardException::class)
    suspend fun deleteById(userUid: String, cardUid: String)

    @Throws(DeleteSecureCardException::class)
    suspend fun deleteAllByUserUid(userUid: String)

    @Throws(FetchSecureCardException::class)
    suspend fun getById(userUid: String, cardUid: String): SecureCardDTO
}