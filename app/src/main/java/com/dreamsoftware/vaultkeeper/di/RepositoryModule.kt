package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import com.dreamsoftware.vaultkeeper.data.firebase.datasource.IAuthDataSource
import com.dreamsoftware.vaultkeeper.data.firebase.datasource.ISecretDataSource
import com.dreamsoftware.vaultkeeper.data.firebase.dto.AuthUserDTO
import com.dreamsoftware.vaultkeeper.data.firebase.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.data.preferences.IPreferencesDataSource
import com.dreamsoftware.vaultkeeper.data.repository.impl.AccountRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.PreferenceRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.SecretRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.SecureCardRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.UserRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AccountMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AuthUserMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.PBEDataMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.SecureCardMapper
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository
import com.dreamsoftware.vaultkeeper.domain.service.ICryptoService
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
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
    fun provideSecureCardMapper(): IBrownieMapper<CardEntity, SecureCardBO> = SecureCardMapper()

    @Provides
    @Singleton
    fun provideAccountMapper(): IBrownieMapper<AccountEntity, AccountBO> = AccountMapper()

    @Provides
    @Singleton
    fun providePBEDataMapper(): IBrownieOneSideMapper<SecretDTO, PBEDataBO> = PBEDataMapper()

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

    @Provides
    @Singleton
    fun provideAccountRepository(
        dataSource: IAccountDataSource,
        accountMapper: IBrownieMapper<AccountEntity, AccountBO>
    ): IAccountRepository =
        AccountRepositoryImpl(
            dataSource,
            accountMapper
        )

    @Provides
    @Singleton
    fun provideSecretRepository(
        secretDataSource: ISecretDataSource,
        passwordGenerator: IPasswordGeneratorService,
        pbeDataMapper: IBrownieOneSideMapper<SecretDTO, PBEDataBO>,
        cryptoService: ICryptoService
    ): ISecretRepository =
        SecretRepositoryImpl(
            secretDataSource,
            passwordGenerator,
            pbeDataMapper,
            cryptoService
        )

    @Provides
    @Singleton
    fun providePreferenceRepository(
        preferenceDataSource: IPreferencesDataSource
    ): IPreferenceRepository =
        PreferenceRepositoryImpl(preferenceDataSource)
}