package com.dreamsoftware.vaultkeeper.data.database.datasource.impl

import com.dreamsoftware.vaultkeeper.data.database.dao.SecureCardDao
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.impl.core.SupportDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccessDatabaseException
import com.dreamsoftware.vaultkeeper.data.database.exception.AccountPasswordRecordNotFoundException
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardRecordNotFoundException
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Implementation of the ISecureCardsDataSource interface.
 * Uses CardDao to perform database operations.
 */
internal class SecureCardsLocalDataSourceImpl(
    private val secureCardDao: SecureCardDao,
    dispatcher: CoroutineDispatcher
) : SupportDataSourceImpl(dispatcher), ISecureCardsLocalDataSource {

    /**
     * Inserts a card into the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @param secureCardEntity The card entity to be inserted.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun insert(secureCardEntity: SecureCardEntity) = safeExecute {
        secureCardDao.insertCard(secureCardEntity).let {
            secureCardDao.getCardsById(secureCardEntity.uid) ?: throw SecureCardRecordNotFoundException("Secure card not found")
        }
    }

    /**
     * Updates an existing card in the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @param secureCardEntity The card entity to be updated.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun update(secureCardEntity: SecureCardEntity) = safeExecute {
        secureCardDao.updateCard(secureCardEntity)
    }

    /**
     * Deletes a card from the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @param cardUid The card entity to be deleted.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun delete(cardUid: String) = safeExecute {
        with(secureCardDao) {
            val account = getCardsById(cardUid)
            account ?: throw AccountPasswordRecordNotFoundException()
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
    @Throws(SecureCardRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun findAll(): List<SecureCardEntity> = safeExecute {
        secureCardDao.getAllCards().takeIf { it.isNotEmpty() } ?: throw SecureCardRecordNotFoundException("No secure cards were found")
    }

    /**
     * Finds a card by its ID.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     * Throws SecureCardNotFoundException if the card is not found.
     *
     * @param cardUid The ID of the card to be retrieved.
     * @return The card entity if found.
     * @throws SecureCardRecordNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun findById(cardUid: String): SecureCardEntity = safeExecute {
        val card = secureCardDao.getCardsById(cardUid)
        card ?: throw SecureCardRecordNotFoundException()
    }

    /**
     * Deletes all cards from the database.
     *
     * Uses Dispatchers.IO to ensure the database operation is performed on an IO-optimized thread.
     * Catches and throws appropriate exceptions.
     *
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(AccessDatabaseException::class)
    override suspend fun deleteAll() = safeExecute {
        secureCardDao.deleteAllCards()
    }
}