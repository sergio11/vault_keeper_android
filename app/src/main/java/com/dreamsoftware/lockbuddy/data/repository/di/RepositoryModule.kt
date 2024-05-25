package com.dreamsoftware.lockbuddy.data.repository.di

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.lockbuddy.data.firebase.datasource.IAuthDataSource
import com.dreamsoftware.lockbuddy.data.firebase.di.FirebaseModule
import com.dreamsoftware.lockbuddy.data.firebase.dto.AuthUserDTO
import com.dreamsoftware.lockbuddy.data.repository.impl.UserRepositoryImpl
import com.dreamsoftware.lockbuddy.data.repository.mapper.AuthUserMapper
import com.dreamsoftware.lockbuddy.domain.model.AuthUserBO
import com.dreamsoftware.lockbuddy.domain.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [FirebaseModule::class])
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
    fun provideUserRepository(
        authDataSource: IAuthDataSource,
        authUserMapper: IBrownieOneSideMapper<AuthUserDTO, AuthUserBO>
    ): IUserRepository =
        UserRepositoryImpl(
            authDataSource,
            authUserMapper
        )
}