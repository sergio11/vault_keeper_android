package com.dreamsoftware.vaultkeeper.data.database.datasource

import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.exception.AccessDatabaseException
import com.dreamsoftware.vaultkeeper.data.database.exception.AccountNotFoundException

interface IAccountLocalDataSource {

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    suspend fun insert(accountEntity: AccountEntity): AccountEntity

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    suspend fun update(accountEntity: AccountEntity)

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    suspend fun delete(uid: String)

    @Throws(AccessDatabaseException::class)
    suspend fun findAll(): List<AccountEntity>

    @Throws(AccountNotFoundException::class, AccessDatabaseException::class)
    suspend fun findById(uid: String): AccountEntity

    @Throws(AccessDatabaseException::class)
    suspend fun deleteAll()
}