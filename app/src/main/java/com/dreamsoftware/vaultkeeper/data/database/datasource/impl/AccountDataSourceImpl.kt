package com.dreamsoftware.vaultkeeper.data.database.datasource.impl

import com.dreamsoftware.vaultkeeper.data.database.dao.AccountDao
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.impl.core.SupportDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccessDatabaseException
import com.dreamsoftware.vaultkeeper.data.database.exception.AccountNotFoundException
import kotlinx.coroutines.CoroutineDispatcher

internal class AccountDataSourceImpl(
    private val accountDao: AccountDao,
    dispatcher: CoroutineDispatcher
): SupportDataSourceImpl(dispatcher), IAccountDataSource {

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    override suspend fun insert(accountEntity: AccountEntity): AccountEntity = safeExecute {
        accountDao.insertAccount(accountEntity).let { id ->
            accountEntity.copy(id = id.toInt())
        }
    }

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    override suspend fun update(accountEntity: AccountEntity) = safeExecute {
        accountDao.updateAccount(accountEntity)
    }

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    override suspend fun delete(id: Int) = safeExecute {
        with(accountDao) {
            val account = getAccountById(id)
            account ?: throw AccountNotFoundException()
            deleteAccount(account)
        }
    }

    @Throws(AccessDatabaseException::class)
    override suspend fun findAll(): List<AccountEntity> = safeExecute {
        accountDao.getAllAccounts()
    }

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    override suspend fun findById(id: Int): AccountEntity = safeExecute {
        val account = accountDao.getAccountById(id)
        account ?: throw AccountNotFoundException()
    }

    @Throws(AccessDatabaseException::class)
    override suspend fun deleteAll() = safeExecute {
        accountDao.deleteAllAccounts()
    }
}