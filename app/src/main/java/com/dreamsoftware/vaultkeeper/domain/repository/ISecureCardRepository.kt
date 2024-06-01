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
    suspend fun deleteById(cardId: Int)

    @Throws(RepositoryOperationException::class)
    suspend fun findAll(): List<SecureCardBO>

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun findById(id: Int): SecureCardBO

    @Throws(RepositoryOperationException::class)
    suspend fun deleteAll()
}