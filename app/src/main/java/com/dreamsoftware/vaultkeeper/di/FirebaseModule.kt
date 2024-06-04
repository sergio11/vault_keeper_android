package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.remote.datasource.IAccountRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.datasource.IAuthRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecretRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecureCardsRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.datasource.impl.AccountsRemoteDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.remote.datasource.impl.AuthRemoteDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.remote.datasource.impl.SecretRemoteDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.remote.datasource.impl.SecureCardsRemoteDataSourceImpl
import com.dreamsoftware.vaultkeeper.data.remote.dto.AccountDTO
import com.dreamsoftware.vaultkeeper.data.remote.dto.AuthUserDTO
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import com.dreamsoftware.vaultkeeper.data.remote.mapper.AccountMapper
import com.dreamsoftware.vaultkeeper.data.remote.mapper.SecretMapper
import com.dreamsoftware.vaultkeeper.data.remote.mapper.SecureCardMapper
import com.dreamsoftware.vaultkeeper.data.remote.mapper.UserAuthenticatedMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

// Dagger module for providing Firebase-related dependencies
@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    /**
     * Provides a singleton instance of UserAuthenticatedMapper.
     * @return a new instance of UserAuthenticatedMapper.
     */
    @Provides
    @Singleton
    fun provideUserAuthenticatedMapper(): IBrownieOneSideMapper<FirebaseUser, AuthUserDTO> = UserAuthenticatedMapper()

    @Provides
    @Singleton
    fun provideSecretMapper(): IBrownieMapper<SecretDTO, Map<String, Any?>> = SecretMapper()


    @Provides
    @Singleton
    fun provideSecureCardMapper():  IBrownieMapper<SecureCardDTO, Map<String, Any?>> = SecureCardMapper()

    @Provides
    @Singleton
    fun provideAccountMapper():  IBrownieMapper<AccountDTO, Map<String, Any?>> = AccountMapper()

    /**
     * Provides a singleton instance of FirebaseAuth.
     * @return the default instance of FirebaseAuth.
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    /**
     * Provide Firebase Store
     */
    @Provides
    @Singleton
    fun provideFirebaseStore() = Firebase.firestore

    /**
     * Provides a singleton instance of IAuthDataSource.
     * @param userAuthenticatedMapper the IBrownieOneSideMapper<FirebaseUser, AuthUserDTO> instance.
     * @param firebaseAuth the FirebaseAuth instance.
     * @return a new instance of AuthDataSourceImpl implementing IAuthDataSource.
     */
    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        userAuthenticatedMapper: IBrownieOneSideMapper<FirebaseUser, AuthUserDTO>,
        firebaseAuth: FirebaseAuth
    ): IAuthRemoteDataSource = AuthRemoteDataSourceImpl(
        userAuthenticatedMapper,
        firebaseAuth
    )

    @Provides
    @Singleton
    fun provideSecretRemoteDataSource(
        fireStore: FirebaseFirestore,
        secretsMapper: IBrownieMapper<SecretDTO, Map<String, Any?>>
    ): ISecretRemoteDataSource = SecretRemoteDataSourceImpl(
        fireStore,
        secretsMapper
    )

    @Provides
    @Singleton
    fun provideSecureCardsRemoteDataSource(
        firebaseStore: FirebaseFirestore,
        mapper: IBrownieMapper<SecureCardDTO, Map<String, Any?>>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): ISecureCardsRemoteDataSource = SecureCardsRemoteDataSourceImpl(
        firebaseStore,
        mapper,
        dispatcher
    )

    @Provides
    @Singleton
    fun provideAccountsRemoteDataSource(
        firebaseStore: FirebaseFirestore,
        mapper: IBrownieMapper<AccountDTO, Map<String, Any?>>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IAccountRemoteDataSource = AccountsRemoteDataSourceImpl(
        firebaseStore,
        mapper,
        dispatcher
    )
}