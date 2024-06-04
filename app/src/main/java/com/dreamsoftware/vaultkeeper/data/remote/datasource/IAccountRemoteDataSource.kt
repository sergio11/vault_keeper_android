package com.dreamsoftware.vaultkeeper.data.remote.datasource

import com.dreamsoftware.vaultkeeper.data.remote.dto.AccountDTO
import com.dreamsoftware.vaultkeeper.data.remote.exception.DeleteAccountException
import com.dreamsoftware.vaultkeeper.data.remote.exception.FetchAccountException
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveAccountException

interface IAccountRemoteDataSource {

    @Throws(SaveAccountException::class)
    suspend fun save(account: AccountDTO)

    @Throws(FetchAccountException::class)
    suspend fun getAllByUserUid(userUid: String): List<AccountDTO>

    @Throws(DeleteAccountException::class)
    suspend fun deleteById(userUid: String, accountUid: String)

    @Throws(DeleteAccountException::class)
    suspend fun deleteAllByUserUid(userUid: String)

    @Throws(FetchAccountException::class)
    suspend fun getById(userUid: String, accountUid: String): AccountDTO
}