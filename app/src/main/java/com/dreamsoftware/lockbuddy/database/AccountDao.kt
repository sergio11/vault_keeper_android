package com.dreamsoftware.lockbuddy.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(accountEntity: com.dreamsoftware.lockbuddy.database.AccountEntity)

    @Update
    suspend fun updateAccount(accountEntity: com.dreamsoftware.lockbuddy.database.AccountEntity)

    @Delete
    suspend fun deleteAccount(accountEntity: com.dreamsoftware.lockbuddy.database.AccountEntity)

    @Query("SELECT * FROM account ORDER BY id ASC")
    fun getAllAccounts(): Flow<List<com.dreamsoftware.lockbuddy.database.AccountEntity>>

    @Query("SELECT * FROM `account` WHERE id = :id")
    fun getAccountById(id: Int): Flow<com.dreamsoftware.lockbuddy.database.AccountEntity>

    @Query("DELETE FROM account")
    fun deleteAllAccounts()

}