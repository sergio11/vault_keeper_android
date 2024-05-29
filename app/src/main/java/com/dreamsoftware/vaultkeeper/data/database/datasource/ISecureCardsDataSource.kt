package com.dreamsoftware.vaultkeeper.data.database.datasource

import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccessDatabaseException
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException

/**
 * Interface for the secure cards data source.
 * Defines all the necessary operations for managing card entities in the database.
 */
interface ISecureCardsDataSource {

    /**
     * Inserts a card into the database.
     *
     * @param cardEntity The card entity to be inserted.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun insertCard(cardEntity: CardEntity): CardEntity

    /**
     * Updates an existing card in the database.
     *
     * @param cardEntity The card entity to be updated.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun updateCard(cardEntity: CardEntity)

    /**
     * Deletes a card from the database.
     *
     * @param cardEntity The card entity to be deleted.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun deleteCard(cardEntity: CardEntity)

    /**
     * Retrieves all cards from the database.
     *
     * @return A list of all card entities.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(AccessDatabaseException::class)
    suspend fun getAllCards(): List<CardEntity>

    /**
     * Finds a card by its ID.
     *
     * @param id The ID of the card to be retrieved.
     * @return The card entity if found.
     * @throws SecureCardNotFoundException If the card is not found.
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(SecureCardNotFoundException::class, AccessDatabaseException::class)
    suspend fun findById(id: Int): CardEntity

    /**
     * Deletes all cards from the database.
     *
     * @throws AccessDatabaseException If any database access error occurs.
     */
    @Throws(AccessDatabaseException::class)
    suspend fun deleteAllCards()
}