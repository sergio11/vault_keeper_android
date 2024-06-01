package com.dreamsoftware.vaultkeeper.data.database.datasource.impl

import com.dreamsoftware.vaultkeeper.data.database.dao.CardDao
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.impl.core.SupportDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccessDatabaseException
import com.dreamsoftware.vaultkeeper.data.database.exception.AccountNotFoundException
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Implementation of the ISecureCardsDataSource interface.
 * Uses CardDao to perform database operations.
 */
internal class SecureCardsDataSourceImpl(
    private val cardDao: CardDao,
    dispatcher: CoroutineDispatcher
) : SupportDataSourceImpl(dispatcher), ISecureCardsDataSource {

    /**
     * Inserts a card into the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @param cardEntity The card entity to be inserted.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun insert(cardEntity: CardEntity) = safeExecute {
        cardDao.insertCard(cardEntity).let { id ->
            cardEntity.copy(id = id.toInt())
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
    override suspend fun update(cardEntity: CardEntity) = safeExecute {
        cardDao.updateCard(cardEntity)
    }

    /**
     * Deletes a card from the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @param id The card entity to be deleted.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun delete(id: Int) = safeExecute {
        with(cardDao) {
            val account = getCardsById(id)
            account ?: throw AccountNotFoundException()
            deleteCard(account)
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
    override suspend fun findAll(): List<CardEntity> = safeExecute {
        cardDao.getAllCards()
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
    override suspend fun findById(id: Int): CardEntity = safeExecute {
        val card = cardDao.getCardsById(id)
        card ?: throw SecureCardNotFoundException()
    }

    /**
     * Deletes all cards from the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @throws AccessDatabaseException If any database access error occurs.
     */
    override suspend fun deleteAll() = safeExecute {
        cardDao.deleteAllCards()
    }
}