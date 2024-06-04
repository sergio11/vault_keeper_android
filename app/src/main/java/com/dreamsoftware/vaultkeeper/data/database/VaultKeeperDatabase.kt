package com.dreamsoftware.vaultkeeper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dreamsoftware.vaultkeeper.data.database.dao.AccountDao
import com.dreamsoftware.vaultkeeper.data.database.dao.SecureCardDao
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity

@Database(
    entities = [AccountEntity::class, SecureCardEntity::class],
    version = 1
)
abstract class VaultKeeperDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun cardDao(): SecureCardDao
}