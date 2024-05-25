package com.dreamsoftware.lockbuddy.domain.di

import com.dreamsoftware.lockbuddy.domain.repository.IUserRepository
import com.dreamsoftware.lockbuddy.domain.usecase.SignInUseCase
import com.dreamsoftware.lockbuddy.domain.usecase.SignOffUseCase
import com.dreamsoftware.lockbuddy.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(
        userRepository: IUserRepository
    ): SignInUseCase =
        SignInUseCase(userRepository = userRepository)

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(
        userRepository: IUserRepository
    ): SignUpUseCase =
        SignUpUseCase(userRepository = userRepository)

    @Provides
    @ViewModelScoped
    fun provideSignOffUseCase(
        userRepository: IUserRepository
    ): SignOffUseCase =
        SignOffUseCase(userRepository = userRepository)
}