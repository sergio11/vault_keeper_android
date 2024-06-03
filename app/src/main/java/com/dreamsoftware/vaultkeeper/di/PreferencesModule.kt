package com.dreamsoftware.vaultkeeper.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dreamsoftware.vaultkeeper.data.preferences.IPreferencesDataSource
import com.dreamsoftware.vaultkeeper.data.preferences.impl.PreferencesDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    private companion object {
        @JvmStatic
        val DATA_STORE_NAME = "DATA_STORE"
    }

    /**
     * Provide Data Store
     * @param context
     */
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        preferencesDataStore(name = DATA_STORE_NAME).getValue(context, String::javaClass)

    /**
     * Provide Preference DataSource
     * @param dataStore
     */
    @Singleton
    @Provides
    fun providePreferenceDataSource(
        dataStore: DataStore<Preferences>
    ): IPreferencesDataSource =
        PreferencesDataSourceImpl(dataStore)
}