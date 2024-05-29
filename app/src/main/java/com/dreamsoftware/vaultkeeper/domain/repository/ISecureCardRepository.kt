package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.CardNotFoundException
import com.dreamsoftware.vaultkeeper.domain.exception.RepositoryOperationException
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO

interface ISecureCardRepository {

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun addCard(card: SecureCardBO): SecureCardBO

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun updateCard(card: SecureCardBO)

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun removeCard(card: SecureCardBO)

    @Throws(RepositoryOperationException::class)
    suspend fun getAllCards(): List<SecureCardBO>

    @Throws(CardNotFoundException::class, RepositoryOperationException::class)
    suspend fun getCardById(id: Int): SecureCardBO

    @Throws(RepositoryOperationException::class)
    suspend fun removeAllCards()
}