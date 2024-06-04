package com.dreamsoftware.vaultkeeper.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(accountEntity: AccountEntity)

    @Update
    suspend fun updateAccount(accountEntity: AccountEntity)

    @Delete
    suspend fun deleteAccount(accountEntity: AccountEntity)

    @Query("SELECT * FROM accounts ORDER BY uid ASC")
    fun getAllAccounts(): List<AccountEntity>

    @Query("SELECT * FROM `accounts` WHERE uid = :uid")
    fun getAccountById(uid: String): AccountEntity?

    @Query("DELETE FROM accounts")
    fun deleteAllAccounts()
}