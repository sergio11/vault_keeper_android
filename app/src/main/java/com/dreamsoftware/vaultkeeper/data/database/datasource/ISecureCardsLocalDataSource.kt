package com.dreamsoftware.vaultkeeper.data.database.datasource

import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccessDatabaseException
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException

/**
 * Interface for the secure cards data source.
 * Defines all the necessary operations for managing card entities in the database.
 */
interface ISecureCardsLocalDataSource {

    /**
     * Inserts a card into the database.
     *
     * @param secureCardEntity The card entity to be inserted.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun insert(secureCardEntity: SecureCardEntity): SecureCardEntity

    /**
     * Updates an existing card in the database.
     *
     * @param secureCardEntity The card entity to be updated.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun update(secureCardEntity: SecureCardEntity)

    /**
     * Deletes a card from the database.
     *
     * @param cardUid The card entity to be deleted.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun delete(cardUid: String)

    /**
     * Retrieves all cards from the database.
     *
     * @return A list of all card entities.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun findAll(): List<SecureCardEntity>

    /**
     * Finds a card by its ID.
     *
     * @param cardUid The ID of the card to be retrieved.
     * @return The card entity if found.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun findById(cardUid: String): SecureCardEntity

    /**
     * Deletes all cards from the database.
     *
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(AccessDatabaseException::class)
    suspend fun deleteAll()
}