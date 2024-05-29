package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository
import com.dreamsoftware.vaultkeeper.domain.usecase.AddCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetCardByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignInUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignOffUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignUpUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.UpdateCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyUserSessionUseCase
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

    @Provides
    @ViewModelScoped
    fun provideVerifyUserSessionUseCase(
        userRepository: IUserRepository
    ): VerifyUserSessionUseCase =
        VerifyUserSessionUseCase(userRepository = userRepository)

    @Provides
    @ViewModelScoped
    fun provideAddCardUseCase(
        secureCardRepository: ISecureCardRepository
    ): AddCardUseCase =
        AddCardUseCase(secureCardRepository = secureCardRepository)

    @Provides
    @ViewModelScoped
    fun provideGetAllCardsUseCase(
        secureCardRepository: ISecureCardRepository
    ): GetAllCardsUseCase =
        GetAllCardsUseCase(secureCardRepository = secureCardRepository)

    @Provides
    @ViewModelScoped
    fun provideGetCardByIdUseCase(
        secureCardRepository: ISecureCardRepository
    ): GetCardByIdUseCase =
        GetCardByIdUseCase(secureCardRepository = secureCardRepository)

    @Provides
    @ViewModelScoped
    fun provideRemoveCardUseCase(
        secureCardRepository: ISecureCardRepository
    ): RemoveCardUseCase =
        RemoveCardUseCase(secureCardRepository = secureCardRepository)

    @Provides
    @ViewModelScoped
    fun provideRemoveAllCardsUseCase(
        secureCardRepository: ISecureCardRepository
    ): RemoveAllCardsUseCase =
        RemoveAllCardsUseCase(secureCardRepository = secureCardRepository)
    
    @Provides
    @ViewModelScoped
    fun provideUpdateCardUseCase(
        secureCardRepository: ISecureCardRepository
    ): UpdateCardUseCase =
        UpdateCardUseCase(secureCardRepository = secureCardRepository)
}