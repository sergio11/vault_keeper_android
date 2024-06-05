package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccountNotFoundException
import com.dreamsoftware.vaultkeeper.data.remote.datasource.IAccountRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.dto.AccountDTO
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService

internal class AccountRepositoryImpl(
    private val localDataSource: IAccountLocalDataSource,
    private val remoteDataSource: IAccountRemoteDataSource,
    private val accountLocalMapper: IBrownieMapper<AccountEntity, AccountBO>,
    private val accountRemoteMapper: IBrownieMapper<AccountDTO, AccountBO>,
    private val dataProtectionService: IDataProtectionService
): SupportRepositoryImpl(), IAccountRepository {

    override suspend fun insert(account: AccountBO): AccountBO = safeExecute {
        val accountProtected = dataProtectionService.wrap(account)
        remoteDataSource.save(accountRemoteMapper.mapOutToIn(accountProtected)).let {
            localDataSource
                .insert(accountLocalMapper.mapOutToIn(accountProtected))
                .let(accountLocalMapper::mapInToOut)
                .let { dataProtectionService.unwrap(it) }
        }
    }

    override suspend fun update(account: AccountBO) = safeExecute {
        val secureCardProtected = dataProtectionService.wrap(account)
        remoteDataSource.save(accountRemoteMapper.mapOutToIn(secureCardProtected)).also {
            localDataSource.update(accountLocalMapper.mapOutToIn(secureCardProtected))
        }
    }

    override suspend fun deleteById(userUid: String, accountUid: String) = safeExecute {
        remoteDataSource.deleteById(userUid, accountUid)
        localDataSource.delete(accountUid)
    }

    override suspend fun findAllByUserId(userUid: String): List<AccountBO> = safeExecute {
        try {
            localDataSource
                .findAll()
                .map(accountLocalMapper::mapInToOut)
        } catch (ex: AccountNotFoundException) {
            remoteDataSource.findAllByUserUid(userUid)
                .map(accountRemoteMapper::mapInToOut)
                .onEach {
                    localDataSource
                        .insert(accountLocalMapper.mapOutToIn(it))
                }
        }.map { dataProtectionService.unwrap(it) }
    }

    override suspend fun findById(userUid: String, accountUid: String): AccountBO = safeExecute {
        try {
            localDataSource.findById(accountUid)
                .let(accountLocalMapper::mapInToOut)
        } catch (ex: AccountNotFoundException) {
            remoteDataSource.findById(userUid, accountUid)
                .let(accountRemoteMapper::mapInToOut)
                .also {
                    localDataSource
                        .insert(accountLocalMapper.mapOutToIn(it))
                }
        }.let { dataProtectionService.unwrap(it) }
    }

    override suspend fun deleteAllByUserId(userUid: String) = safeExecute {
        remoteDataSource.deleteAllByUserUid(userUid)
        localDataSource.deleteAll()
    }
}