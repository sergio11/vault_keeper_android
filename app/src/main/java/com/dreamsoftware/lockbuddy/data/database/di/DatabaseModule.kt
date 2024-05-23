package com.dreamsoftware.lockbuddy.data.database.di

import android.content.Context
import androidx.room.Room
import com.dreamsoftware.lockbuddy.data.database.LockBuddyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}