package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.AccountNotFoundException
import com.dreamsoftware.vaultkeeper.domain.exception.RepositoryOperationException
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO

interface IAccountRepository {

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun insert(account: AccountBO): AccountBO

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun update(account: AccountBO)

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun deleteById(accountUid: String)

    @Throws(RepositoryOperationException::class)
    suspend fun findAll(): List<AccountBO>

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun findById(accountUid: String): AccountBO

    @Throws(RepositoryOperationException::class)
    suspend fun deleteAll()
}