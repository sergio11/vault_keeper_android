package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.CardNotFoundException
import com.dreamsoftware.vaultkeeper.domain.exception.RepositoryOperationException
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO

interface ISecureCardRepository {

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun insert(card: SecureCardBO): SecureCardBO

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun update(card: SecureCardBO)

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun deleteById(userUid: String, cardUid: String)

    @Throws(RepositoryOperationException::class)
    suspend fun findAllByUser(userUid: String): List<SecureCardBO>

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun findById(userUid: String, cardUid: String): SecureCardBO

    @Throws(RepositoryOperationException::class)
    suspend fun deleteAllByUserId(userUid: String)
}