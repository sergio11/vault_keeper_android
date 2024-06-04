package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException
import com.dreamsoftware.vaultkeeper.data.remote.datasource.IAccountRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.exception.CardNotFoundException
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService

internal class AccountRepositoryImpl(
    private val localDataSource: IAccountLocalDataSource,
    private val remoteDataSource: IAccountRemoteDataSource,
    private val accountMapper: IBrownieMapper<AccountEntity, AccountBO>,
    private val dataProtectionService: IDataProtectionService
): SupportRepositoryImpl(), IAccountRepository {

    override suspend fun insert(account: AccountBO): AccountBO = safeExecute {
        localDataSource
            .insert(accountMapper.mapOutToIn(dataProtectionService.wrap(account)))
            .let(accountMapper::mapInToOut)
            .let { dataProtectionService.unwrap(it) }
    }

    override suspend fun update(account: AccountBO) = safeExecute {
        localDataSource.update(accountMapper.mapOutToIn(dataProtectionService.wrap(account)))
    }

    override suspend fun deleteById(accountUid: String) = safeExecute {
        localDataSource.delete(accountUid)
    }

    override suspend fun findAll(): List<AccountBO> = safeExecute {
        localDataSource
            .findAll()
            .map(accountMapper::mapInToOut)
            .map { dataProtectionService.unwrap(it) }
    }

    override suspend fun findById(accountUid: String): AccountBO = safeExecute {
        try {
            localDataSource.findById(accountUid)
                .let(accountMapper::mapInToOut)
                .let { dataProtectionService.unwrap(it) }
        } catch (ex: SecureCardNotFoundException) {
            throw CardNotFoundException("Account with ID $accountUid not found", ex)
        }
    }

    override suspend fun deleteAll()  = safeExecute {
        localDataSource.deleteAll()
    }
}