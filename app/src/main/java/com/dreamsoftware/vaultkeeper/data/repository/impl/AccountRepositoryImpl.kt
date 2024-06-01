package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.SecureCardNotFoundException
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.exception.CardNotFoundException
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository

internal class AccountRepositoryImpl(
    private val dataSource: IAccountDataSource,
    private val accountMapper: IBrownieMapper<AccountEntity, AccountBO>
): SupportRepositoryImpl(), IAccountRepository {

    override suspend fun insert(account: AccountBO): AccountBO = safeExecute {
        dataSource
            .insert(accountMapper.mapOutToIn(account))
            .let(accountMapper::mapInToOut)
    }

    override suspend fun update(account: AccountBO) = safeExecute {
        dataSource.update(accountMapper.mapOutToIn(account))
    }

    override suspend fun deleteById(accountId: Int) = safeExecute {
        dataSource.delete(accountId)
    }

    override suspend fun findAll(): List<AccountBO> = safeExecute {
        dataSource
            .findAll()
            .map(accountMapper::mapInToOut)
    }

    override suspend fun findById(id: Int): AccountBO = safeExecute {
        try {
            dataSource.findById(id)
                .let(accountMapper::mapInToOut)
        } catch (ex: SecureCardNotFoundException) {
            throw CardNotFoundException("Account with ID $id not found", ex)
        }
    }

    override suspend fun deleteAll()  = safeExecute {
        dataSource.deleteAll()
    }
}