package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException
import com.dreamsoftware.vaultkeeper.domain.exception.CardNotFoundException
import com.dreamsoftware.vaultkeeper.domain.exception.RepositoryOperationException
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

internal class SecureCardRepositoryImpl(
    private val dataSource: ISecureCardsDataSource,
    private val secureCardUserMapper: IBrownieMapper<CardEntity, SecureCardBO>
): ISecureCardRepository {
    override suspend fun addCard(card: SecureCardBO): SecureCardBO =
        try {
            dataSource
                .insertCard(secureCardUserMapper.mapOutToIn(card))
                .let(secureCardUserMapper::mapInToOut)
        } catch (ex: Exception) {
            throw RepositoryOperationException("Failed to add card", ex)
        }

    override suspend fun updateCard(card: SecureCardBO) {
        try {
            dataSource.updateCard(secureCardUserMapper.mapOutToIn(card))
        } catch (ex: Exception) {
            throw RepositoryOperationException("Failed to update card", ex)
        }
    }

    override suspend fun removeCard(card: SecureCardBO) {
        try {
            dataSource.deleteCard(secureCardUserMapper.mapOutToIn(card))
        } catch (ex: Exception) {
            throw RepositoryOperationException("Failed to remove card", ex)
        }
    }

    override suspend fun getAllCards(): List<SecureCardBO> {
        return try {
            dataSource
                .getAllCards()
                .map(secureCardUserMapper::mapInToOut)
        } catch (ex: Exception) {
            throw RepositoryOperationException("Failed to retrieve all cards", ex)
        }
    }

    override suspend fun getCardById(id: Int): SecureCardBO {
        return try {
            dataSource.findById(id)
                .let(secureCardUserMapper::mapInToOut)
        } catch (ex: SecureCardNotFoundException) {
            throw CardNotFoundException("Card with ID $id not found", ex)
        } catch (ex: Exception) {
            throw RepositoryOperationException("Failed to retrieve card with ID $id", ex)
        }
    }

    override suspend fun removeAllCards() {
        try {
            dataSource.deleteAllCards()
        } catch (ex: Exception) {
            throw RepositoryOperationException("Failed to remove all cards", ex)
        }
    }
}