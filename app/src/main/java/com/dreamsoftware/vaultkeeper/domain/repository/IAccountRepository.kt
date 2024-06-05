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
    suspend fun deleteById(userUid: String, accountUid: String)

    @Throws(RepositoryOperationException::class)
    suspend fun findAllByUserId(userUid: String): List<AccountBO>

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun findById(userUid: String, accountUid: String): AccountBO

    @Throws(RepositoryOperationException::class)
    suspend fun deleteAllByUserId(userUid: String)
}