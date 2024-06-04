package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecureCardsRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.exception.CardNotFoundException
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService

internal class SecureCardRepositoryImpl(
    private val localDataSource: ISecureCardsLocalDataSource,
    private val remoteDataSource: ISecureCardsRemoteDataSource,
    private val secureCardLocalUserMapper: IBrownieMapper<SecureCardEntity, SecureCardBO>,
    private val dataProtectionService: IDataProtectionService
): SupportRepositoryImpl(), ISecureCardRepository {

    override suspend fun insert(card: SecureCardBO): SecureCardBO = safeExecute {
        localDataSource
            .insert(secureCardLocalUserMapper.mapOutToIn(dataProtectionService.wrap(card)))
            .let(secureCardLocalUserMapper::mapInToOut)
            .let { dataProtectionService.unwrap(it) }
    }

    override suspend fun update(card: SecureCardBO) = safeExecute {
        localDataSource.update(secureCardLocalUserMapper.mapOutToIn(card))
    }

    override suspend fun deleteById(cardUid: String) = safeExecute {
        localDataSource.delete(cardUid)
    }

    override suspend fun findAll(): List<SecureCardBO> = safeExecute {
        localDataSource
            .findAll()
            .map(secureCardLocalUserMapper::mapInToOut)
            .map { dataProtectionService.unwrap(it) }
    }

    override suspend fun findById(cardUid: String): SecureCardBO = safeExecute {
        try {
            localDataSource.findById(cardUid)
                .let(secureCardLocalUserMapper::mapInToOut)
                .let { dataProtectionService.unwrap(it) }
        } catch (ex: SecureCardNotFoundException) {
            throw CardNotFoundException("Card with ID $cardUid not found", ex)
        }
    }

    override suspend fun deleteAll()  = safeExecute {
        localDataSource.deleteAll()
    }
}