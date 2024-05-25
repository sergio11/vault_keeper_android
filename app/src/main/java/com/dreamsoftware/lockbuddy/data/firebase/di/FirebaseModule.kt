package com.dreamsoftware.lockbuddy.data.firebase.di

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.lockbuddy.data.firebase.datasource.IAuthDataSource
import com.dreamsoftware.lockbuddy.data.firebase.datasource.impl.AuthDataSourceImpl
import com.dreamsoftware.lockbuddy.data.firebase.dto.AuthUserDTO
import com.dreamsoftware.lockbuddy.data.firebase.mapper.UserAuthenticatedMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    /**
     * Provides a singleton instance of FirebaseAuth.
     * @return the default instance of FirebaseAuth.
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Provides a singleton instance of IAuthDataSource.
     * @param userAuthenticatedMapper the UserAuthenticatedMapper instance.
     * @param firebaseAuth the FirebaseAuth instance.
     * @return a new instance of AuthDataSourceImpl implementing IAuthDataSource.
     */
    @Provides
    @Singleton
    fun provideAuthDataSource(
        userAuthenticatedMapper: UserAuthenticatedMapper,
        firebaseAuth: FirebaseAuth
    ): IAuthDataSource = AuthDataSourceImpl(
        userAuthenticatedMapper,
        firebaseAuth
    )
}