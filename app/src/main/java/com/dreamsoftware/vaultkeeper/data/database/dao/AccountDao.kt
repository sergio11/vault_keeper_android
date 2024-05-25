package com.dreamsoftware.vaultkeeper.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(accountEntity: AccountEntity)

    @Update
    suspend fun updateAccount(accountEntity: AccountEntity)

    @Delete
    suspend fun deleteAccount(accountEntity: AccountEntity)

    @Query("SELECT * FROM account ORDER BY id ASC")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM `account` WHERE id = :id")
    fun getAccountById(id: Int): Flow<AccountEntity>

    @Query("DELETE FROM account")
    fun deleteAllAccounts()
}