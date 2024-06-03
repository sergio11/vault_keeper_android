package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.exception.CardNotFoundException
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService

internal class SecureCardRepositoryImpl(
    private val dataSource: ISecureCardsDataSource,
    private val secureCardUserMapper: IBrownieMapper<CardEntity, SecureCardBO>,
    private val dataProtectionService: IDataProtectionService
): SupportRepositoryImpl(), ISecureCardRepository {

    override suspend fun insert(card: SecureCardBO): SecureCardBO = safeExecute {
        dataSource
            .insert(secureCardUserMapper.mapOutToIn(dataProtectionService.wrap(card)))
            .let(secureCardUserMapper::mapInToOut)
            .let { dataProtectionService.unwrap(it) }
    }

    override suspend fun update(card: SecureCardBO) = safeExecute {
        dataSource.update(secureCardUserMapper.mapOutToIn(card))
    }

    override suspend fun deleteById(cardId: Int) = safeExecute {
        dataSource.delete(cardId)
    }

    override suspend fun findAll(): List<SecureCardBO> = safeExecute {
        dataSource
            .findAll()
            .map(secureCardUserMapper::mapInToOut)
            .map { dataProtectionService.unwrap(it) }
    }

    override suspend fun findById(id: Int): SecureCardBO = safeExecute {
        try {
            dataSource.findById(id)
                .let(secureCardUserMapper::mapInToOut)
                .let { dataProtectionService.unwrap(it) }
        } catch (ex: SecureCardNotFoundException) {
            throw CardNotFoundException("Card with ID $id not found", ex)
        }
    }

    override suspend fun deleteAll()  = safeExecute {
        dataSource.deleteAll()
    }
}