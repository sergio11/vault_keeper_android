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
import com.dreamsoftware.vaultkeeper.data.remote.dto.AccountDTO
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import com.dreamsoftware.vaultkeeper.data.repository.impl.AccountRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.PreferenceRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.SecretRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.SecureCardRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.impl.UserRepositoryImpl
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AccountLocalMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AccountRemoteMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AuthUserInfo
import com.dreamsoftware.vaultkeeper.data.repository.mapper.AuthUserMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.PBEDataMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.SecureCardLocalMapper
import com.dreamsoftware.vaultkeeper.data.repository.mapper.SecureCardRemoteMapper
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
    fun provideAuthUserMapper(): IBrownieOneSideMapper<AuthUserInfo, AuthUserBO> = AuthUserMapper()

    @Provides
    @Singleton
    fun provideSecureCardLocalMapper(): IBrownieMapper<SecureCardEntity, SecureCardBO> = SecureCardLocalMapper()

    @Provides
    @Singleton
    fun provideSecureCardRemoteMapper(): IBrownieMapper<SecureCardDTO, SecureCardBO> = SecureCardRemoteMapper()

    @Provides
    @Singleton
    fun provideAccountLocalMapper(): IBrownieMapper<AccountEntity, AccountBO> = AccountLocalMapper()

    @Provides
    @Singleton
    fun provideAccountRemoteMapper(): IBrownieMapper<AccountDTO, AccountBO> = AccountRemoteMapper()

    @Provides
    @Singleton
    fun providePBEDataMapper(): IBrownieOneSideMapper<SecretDTO, PBEDataBO> = PBEDataMapper()

    @Provides
    @Singleton
    fun provideUserRepository(
        authDataSource: IAuthRemoteDataSource,
        secretRepository: ISecretRepository,
        authUserMapper: IBrownieOneSideMapper<AuthUserInfo, AuthUserBO>
    ): IUserRepository =
        UserRepositoryImpl(
            authDataSource,
            secretRepository,
            authUserMapper
        )

    @Provides
    @Singleton
    fun provideSecureCardRepository(
        localDataSource: ISecureCardsLocalDataSource,
        remoteDataSource: ISecureCardsRemoteDataSource,
        secureCardLocalUserMapper: IBrownieMapper<SecureCardEntity, SecureCardBO>,
        secureCardRemoteUserMapper: IBrownieMapper<SecureCardDTO, SecureCardBO>,
        dataProtectionService: IDataProtectionService
    ): ISecureCardRepository =
        SecureCardRepositoryImpl(
            localDataSource,
            remoteDataSource,
            secureCardLocalUserMapper,
            secureCardRemoteUserMapper,
            dataProtectionService
        )

    @Provides
    @Singleton
    fun provideAccountRepository(
        localDataSource: IAccountLocalDataSource,
        remoteDataSource: IAccountRemoteDataSource,
        accountLocalMapper: IBrownieMapper<AccountEntity, AccountBO>,
        accountRemoteMapper: IBrownieMapper<AccountDTO, AccountBO>,
        dataProtectionService: IDataProtectionService
    ): IAccountRepository =
        AccountRepositoryImpl(
            localDataSource,
            remoteDataSource,
            accountLocalMapper,
            accountRemoteMapper,
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