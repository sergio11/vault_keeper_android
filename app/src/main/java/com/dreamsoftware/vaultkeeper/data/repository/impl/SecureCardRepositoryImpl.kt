package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecureCardsRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService

internal class SecureCardRepositoryImpl(
    private val localDataSource: ISecureCardsLocalDataSource,
    private val remoteDataSource: ISecureCardsRemoteDataSource,
    private val secureCardLocalUserMapper: IBrownieMapper<SecureCardEntity, SecureCardBO>,
    private val secureCardRemoteUserMapper: IBrownieMapper<SecureCardDTO, SecureCardBO>,
    private val dataProtectionService: IDataProtectionService
) : SupportRepositoryImpl(), ISecureCardRepository {

    override suspend fun insert(card: SecureCardBO): SecureCardBO = safeExecute {
        val secureCardProtected = dataProtectionService.wrap(card)
        remoteDataSource.save(secureCardRemoteUserMapper.mapOutToIn(secureCardProtected)).let {
            localDataSource
                .insert(secureCardLocalUserMapper.mapOutToIn(secureCardProtected))
                .let(secureCardLocalUserMapper::mapInToOut)
                .let { dataProtectionService.unwrap(it) }
        }
    }

    override suspend fun update(card: SecureCardBO) = safeExecute {
        val secureCardProtected = dataProtectionService.wrap(card)
        remoteDataSource.save(secureCardRemoteUserMapper.mapOutToIn(secureCardProtected)).also {
            localDataSource.update(secureCardLocalUserMapper.mapOutToIn(secureCardProtected))
        }

    }

    override suspend fun deleteById(userUid: String, cardUid: String) = safeExecute {
        remoteDataSource.deleteById(userUid, cardUid)
        localDataSource.delete(cardUid)
    }

    override suspend fun findAllByUser(userUid: String): List<SecureCardBO> = safeExecute {
        try {
            localDataSource
                .findAll()
                .map(secureCardLocalUserMapper::mapInToOut)
        } catch (ex: SecureCardNotFoundException) {
            remoteDataSource.findAllByUserUid(userUid)
                .map(secureCardRemoteUserMapper::mapInToOut)
                .onEach {
                    localDataSource
                        .insert(secureCardLocalUserMapper.mapOutToIn(it))
                }
        }.map { dataProtectionService.unwrap(it) }
    }

    override suspend fun findById(userUid: String, cardUid: String): SecureCardBO = safeExecute {
        try {
            localDataSource.findById(cardUid)
                .let(secureCardLocalUserMapper::mapInToOut)
        } catch (ex: SecureCardNotFoundException) {
            remoteDataSource.findById(userUid, cardUid)
                .let(secureCardRemoteUserMapper::mapInToOut)
                .also {
                    localDataSource
                        .insert(secureCardLocalUserMapper.mapOutToIn(it))
                }
        }.let { dataProtectionService.unwrap(it) }
    }

    override suspend fun deleteAllByUser(userUid: String) = safeExecute {
        remoteDataSource.deleteAllByUserUid(userUid)
        localDataSource.deleteAll()
    }
}