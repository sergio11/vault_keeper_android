package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.vaultkeeper.domain.service.ICryptoService
import com.dreamsoftware.vaultkeeper.service.crypto.CryptoServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    /**
     * Provide Crypto service impl
     */
    @Provides
    @Singleton
    fun provideCryptoService(): ICryptoService = CryptoServiceImpl()
}