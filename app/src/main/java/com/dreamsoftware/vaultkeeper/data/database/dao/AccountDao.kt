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
    suspend fun insertAccount(accountEntity: AccountEntity): Long

    @Update
    suspend fun updateAccount(accountEntity: AccountEntity)

    @Delete
    suspend fun deleteAccount(accountEntity: AccountEntity)

    @Query("SELECT * FROM accounts ORDER BY id ASC")
    fun getAllAccounts(): List<AccountEntity>

    @Query("SELECT * FROM `accounts` WHERE id = :id")
    fun getAccountById(id: Int): AccountEntity?

    @Query("DELETE FROM accounts")
    fun deleteAllAccounts()
}