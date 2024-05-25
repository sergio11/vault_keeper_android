package com.dreamsoftware.vaultkeeper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dreamsoftware.vaultkeeper.data.database.dao.AccountDao
import com.dreamsoftware.vaultkeeper.data.database.dao.CardDao
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity

@Database(
    entities = [AccountEntity::class, CardEntity::class],
    version = 1
)
abstract class LockBuddyDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun cardDao(): CardDao
}