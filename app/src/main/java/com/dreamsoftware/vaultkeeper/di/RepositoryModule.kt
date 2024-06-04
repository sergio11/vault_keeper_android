package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.database.datasource.IAccountLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.datasource.ISecureCardsLocalDataSource
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity
import com.dreamsoftware.vaultkeeper.data.preferences.IPreferencesDataSource
import com.dreamsoftware.vaultkeeper.data.remote.datasource.IAccountRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.datasource.IAuthRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecretRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecureCardsRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.dto.AuthUserDTO
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.data.repository.impl.AccountRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.PreferenceRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.SecretRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.SecureCardRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.UserRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AccountMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AuthUserMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.PBEDataMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.SecureCardLocalMapper
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
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService
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
    fun provideSecureCardMapper(): IBrownieMapper<SecureCardEntity, SecureCardBO> = SecureCardLocalMapper()

    @Provides
    @Singleton
    fun provideAccountMapper(): IBrownieMapper<AccountEntity, AccountBO> = AccountMapper()

    @Provides
    @Singleton
    fun providePBEDataMapper(): IBrownieOneSideMapper<SecretDTO, PBEDataBO> = PBEDataMapper()

    @Provides
    @Singleton
    fun provideUserRepository(
        authDataSource: IAuthRemoteDataSource,
        authUserMapper: IBrownieOneSideMapper<AuthUserDTO, AuthUserBO>
    ): IUserRepository =
        UserRepositoryImpl(
            authDataSource,
            authUserMapper
        )

    @Provides
    @Singleton
    fun provideSecureCardRepository(
        localDataSource: ISecureCardsLocalDataSource,
        remoteDataSource: ISecureCardsRemoteDataSource,
        secureCardUserMapper: IBrownieMapper<SecureCardEntity, SecureCardBO>,
        dataProtectionService: IDataProtectionService
    ): ISecureCardRepository =
        SecureCardRepositoryImpl(
            localDataSource,
            remoteDataSource,
            secureCardUserMapper,
            dataProtectionService
        )

    @Provides
    @Singleton
    fun provideAccountRepository(
        localDataSource: IAccountLocalDataSource,
        remoteDataSource: IAccountRemoteDataSource,
        accountMapper: IBrownieMapper<AccountEntity, AccountBO>,
        dataProtectionService: IDataProtectionService
    ): IAccountRepository =
        AccountRepositoryImpl(
            localDataSource,
            remoteDataSource,
            accountMapper,
            dataProtectionService
        )

    @Provides
    @Singleton
    fun provideSecretRepository(
        secretDataSource: ISecretRemoteDataSource,
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