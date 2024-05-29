package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import com.dreamsoftware.vaultkeeper.data.firebase.datasource.IAuthDataSource
import com.dreamsoftware.vaultkeeper.data.firebase.dto.AuthUserDTO
import com.dreamsoftware.vaultkeeper.data.repository.impl.SecureCardRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.UserRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AuthUserMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.SecureCardUserMapper
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [FirebaseModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    /**
     * Provide Auth User Mapper
     */
    @Provides
    @Singleton
    fun provideAuthUserMapper(): IBrownieOneSideMapper<AuthUserDTO, AuthUserBO> = AuthUserMapper()

    @Provides
    @Singleton
    fun provideSecureCardUserMapper(): IBrownieMapper<CardEntity, SecureCardBO> = SecureCardUserMapper()

    @Provides
    @Singleton
    fun provideUserRepository(
        authDataSource: IAuthDataSource,
        authUserMapper: IBrownieOneSideMapper<AuthUserDTO, AuthUserBO>
    ): IUserRepository =
        UserRepositoryImpl(
            authDataSource,
            authUserMapper
        )


    @Provides
    @Singleton
    fun provideSecureCardRepository(
        dataSource: ISecureCardsDataSource,
        secureCardUserMapper: IBrownieMapper<CardEntity, SecureCardBO>
    ): ISecureCardRepository =
        SecureCardRepositoryImpl(
            dataSource,
            secureCardUserMapper
        )
}