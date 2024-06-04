package com.dreamsoftware.vaultkeeper.data.database.datasource.impl

import com.dreamsoftware.vaultkeeper.data.database.dao.AccountDao
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.impl.core.SupportDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccessDatabaseException
import com.dreamsoftware.vaultkeeper.data.database.exception.AccountNotFoundException
import kotlinx.coroutines.CoroutineDispatcher

internal class AccountLocalDataSourceImpl(
    private val accountDao: AccountDao,
    dispatcher: CoroutineDispatcher
): SupportDataSourceImpl(dispatcher), IAccountLocalDataSource {

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    override suspend fun insert(accountEntity: AccountEntity): AccountEntity = safeExecute {
        accountDao.insertAccount(accountEntity).let {
            accountDao.getAccountById(accountEntity.uid) ?: throw AccountNotFoundException("Account not found")
        }
    }

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    override suspend fun update(accountEntity: AccountEntity) = safeExecute {
        accountDao.updateAccount(accountEntity)
    }

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    override suspend fun delete(uid: String) = safeExecute {
        with(accountDao) {
            val account = getAccountById(uid)
            account ?: throw AccountNotFoundException()
            deleteAccount(account)
        }
    }

    @Throws(AccessDatabaseException::class)
    override suspend fun findAll(): List<AccountEntity> = safeExecute {
        accountDao.getAllAccounts()
    }

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    override suspend fun findById(uid: String): AccountEntity = safeExecute {
        val account = accountDao.getAccountById(uid)
        account ?: throw AccountNotFoundException()
    }

    @Throws(AccessDatabaseException::class)
    override suspend fun deleteAll() = safeExecute {
        accountDao.deleteAllAccounts()
    }
}