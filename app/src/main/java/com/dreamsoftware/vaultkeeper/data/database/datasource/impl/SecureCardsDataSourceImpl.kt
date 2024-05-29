package com.dreamsoftware.vaultkeeper.data.database.datasource.impl

import com.dreamsoftware.vaultkeeper.data.database.dao.CardDao
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccessDatabaseException
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of the ISecureCardsDataSource interface.
 * Uses CardDao to perform database operations.
 */
internal class SecureCardsDataSourceImpl(
    private val cardDao: CardDao
) : ISecureCardsDataSource {

    /**
     * Inserts a card into the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @param cardEntity The card entity to be inserted.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun insertCard(cardEntity: CardEntity) = withContext(Dispatchers.IO) {
        try {
            cardDao.insertCard(cardEntity)
        } catch (ex: Exception) {
            throw AccessDatabaseException()
        }
    }

    /**
     * Updates an existing card in the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @param cardEntity The card entity to be updated.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun updateCard(cardEntity: CardEntity) = withContext(Dispatchers.IO) {
        try {
            cardDao.updateCard(cardEntity)
        } catch (ex: Exception) {
            throw AccessDatabaseException()
        }
    }

    /**
     * Deletes a card from the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @param cardEntity The card entity to be deleted.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun deleteCard(cardEntity: CardEntity) = withContext(Dispatchers.IO) {
        try {
            cardDao.deleteCard(cardEntity)
        } catch (ex: Exception) {
            throw AccessDatabaseException()
        }
    }

    /**
     * Retrieves all cards from the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @return A list of all card entities.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun getAllCards(): List<CardEntity> = withContext(Dispatchers.IO) {
        try {
            cardDao.getAllCards()
        } catch (ex: Exception) {
            throw AccessDatabaseException()
        }
    }

    /**
     * Finds a card by its ID.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     * Throws SecureCardNotFoundException if the card is not found.
     *
     * @param id The ID of the card to be retrieved.
     * @return The card entity if found.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun findById(id: Int): CardEntity = withContext(Dispatchers.IO) {
        try {
            val card = cardDao.getCardsById(id)
            card ?: throw SecureCardNotFoundException()
        } catch (ex: Exception) {
            if (ex is SecureCardNotFoundException) throw ex
            throw AccessDatabaseException()
        }
    }

    /**
     * Deletes all cards from the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun deleteAllCards() = withContext(Dispatchers.IO) {
        try {
            cardDao.deleteAllCards()
        } catch (ex: Exception) {
            throw AccessDatabaseException()
        }
    }
}