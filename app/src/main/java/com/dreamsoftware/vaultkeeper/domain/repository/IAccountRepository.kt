package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.AccountNotFoundException
import com.dreamsoftware.vaultkeeper.domain.exception.RepositoryOperationException
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO

interface IAccountRepository {

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun insert(account: AccountPasswordBO): AccountPasswordBO

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun update(account: AccountPasswordBO)

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun update(accountList: List<AccountPasswordBO>)

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun deleteById(userUid: String, accountUid: String)

    @Throws(RepositoryOperationException::class)
    suspend fun findAllByUserIdWhere(userUid: String, term: String?): List<AccountPasswordBO>

    @Throws(RepositoryOperationException::class)
    suspend fun findAllByUserId(userUid: String): List<AccountPasswordBO>

    @Throws(AccountNotFoundException::class, RepositoryOperationException::class)
    suspend fun findById(userUid: String, accountUid: String): AccountPasswordBO

    @Throws(RepositoryOperationException::class)
    suspend fun deleteAllByUserId(userUid: String)
}