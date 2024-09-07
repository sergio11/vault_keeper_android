package com.dreamsoftware.vaultkeeper.di

import android.content.Context
import androidx.room.Room
import com.dreamsoftware.vaultkeeper.data.database.VaultKeeperDatabase
import com.dreamsoftware.vaultkeeper.data.database.dao.AccountDao
import com.dreamsoftware.vaultkeeper.data.database.dao.SecureCardDao
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.impl.AccountLocalDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.database.datasource.impl.SecureCardsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        VaultKeeperDatabase::class.java,
        "vault_keeper_database"
    ).build()

    @Singleton
    @Provides
    fun provideAccountDao(db: VaultKeeperDatabase) = db.accountDao()

    @Singleton
    @Provides
    fun provideCardDao(db: VaultKeeperDatabase) = db.cardDao()

    @Singleton
    @Provides
    fun provideSecureCardsDataSource(
        secureCardDao: SecureCardDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): ISecureCardsLocalDataSource = SecureCardsLocalDataSourceImpl(secureCardDao, dispatcher)


    @Singleton
    @Provides
    fun provideAccountDataSource(
        accountDao: AccountDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IAccountLocalDataSource = AccountLocalDataSourceImpl(accountDao, dispatcher)
}