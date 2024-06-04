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
    suspend fun deleteById(cardUid: String)

    @Throws(RepositoryOperationException::class)
    suspend fun findAll(): List<SecureCardBO>

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun findById(cardUid: String): SecureCardBO

    @Throws(RepositoryOperationException::class)
    suspend fun deleteAll()
}