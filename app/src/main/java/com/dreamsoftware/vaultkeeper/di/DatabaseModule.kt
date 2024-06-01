package com.dreamsoftware.vaultkeeper.di

import android.content.Context
import androidx.room.Room
import com.dreamsoftware.vaultkeeper.data.database.LockBuddyDatabase
import com.dreamsoftware.vaultkeeper.data.database.dao.AccountDao
import com.dreamsoftware.vaultkeeper.data.database.dao.CardDao
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.impl.AccountDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.database.datasource.impl.SecureCardsDataSourceImpl
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
        LockBuddyDatabase::class.java,
        "lockbuddy_database"
    ).build()

    @Singleton
    @Provides
    fun provideAccountDao(db: LockBuddyDatabase) = db.accountDao()

    @Singleton
    @Provides
    fun provideCardDao(db: LockBuddyDatabase) = db.cardDao()

    @Singleton
    @Provides
    fun provideSecureCardsDataSource(
        cardDao: CardDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): ISecureCardsDataSource = SecureCardsDataSourceImpl(cardDao, dispatcher)


    @Singleton
    @Provides
    fun provideAccountDataSource(
        accountDao: AccountDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IAccountDataSource = AccountDataSourceImpl(accountDao, dispatcher)
}