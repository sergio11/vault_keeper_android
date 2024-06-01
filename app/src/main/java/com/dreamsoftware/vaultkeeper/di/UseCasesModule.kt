package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAccountByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllAccountsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetCardByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllAccountsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignInUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignOffUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignUpUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyUserSessionUseCase
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module(includes = [ValidatorModule::class])
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

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
    fun provideSaveCardUseCase(
        secureCardRepository: ISecureCardRepository,
        secureCardValidator: IBusinessEntityValidator<SecureCardBO>
    ): SaveCardUseCase =
        SaveCardUseCase(
            secureCardRepository = secureCardRepository,
            secureCardValidator = secureCardValidator
        )

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
    fun provideSaveAccountUseCase(
        accountRepository: IAccountRepository,
        accountValidator: IBusinessEntityValidator<AccountBO>
    ): SaveAccountUseCase =
        SaveAccountUseCase(
            accountRepository = accountRepository,
            accountValidator = accountValidator
        )

    @Provides
    @ViewModelScoped
    fun provideGetAllAccountsUseCase(
        accountRepository: IAccountRepository
    ): GetAllAccountsUseCase =
        GetAllAccountsUseCase(accountRepository = accountRepository)

    @Provides
    @ViewModelScoped
    fun provideGetAccountByIdUseCase(
        accountRepository: IAccountRepository
    ): GetAccountByIdUseCase =
        GetAccountByIdUseCase(accountRepository = accountRepository)

    @Provides
    @ViewModelScoped
    fun provideRemoveAccountUseCase(
        accountRepository: IAccountRepository
    ): RemoveAccountUseCase =
        RemoveAccountUseCase(accountRepository = accountRepository)

    @Provides
    @ViewModelScoped
    fun provideRemoveAllAccountsUseCase(
        accountRepository: IAccountRepository
    ): RemoveAllAccountsUseCase =
        RemoveAllAccountsUseCase(accountRepository = accountRepository)
}