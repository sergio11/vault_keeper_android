package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.model.AuthRequestBO
import com.dreamsoftware.vaultkeeper.domain.model.SaveMasterKeyBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.model.SignUpBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAccountByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllAccountsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllCredentialsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAuthenticateUserDetailUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetCardByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllAccountsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllCredentialsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemovePasswordAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveSecureCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveMasterKeyUseCase
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
        userRepository: IUserRepository,
        preferenceRepository: IPreferenceRepository,
        validator: IBusinessEntityValidator<AuthRequestBO>
    ): SignInUseCase =
        SignInUseCase(
            userRepository = userRepository,
            preferenceRepository = preferenceRepository,
            validator = validator
        )

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(
        preferenceRepository: IPreferenceRepository,
        userRepository: IUserRepository,
        validator: IBusinessEntityValidator<SignUpBO>
    ): SignUpUseCase =
        SignUpUseCase(
            userRepository = userRepository,
            preferenceRepository = preferenceRepository,
            validator = validator
        )

    @Provides
    @ViewModelScoped
    fun provideSignOffUseCase(
        userRepository: IUserRepository,
        preferenceRepository: IPreferenceRepository
    ): SignOffUseCase =
        SignOffUseCase(
            userRepository = userRepository,
            preferenceRepository = preferenceRepository
        )

    @Provides
    @ViewModelScoped
    fun provideVerifyUserSessionUseCase(
        userRepository: IUserRepository
    ): VerifyUserSessionUseCase =
        VerifyUserSessionUseCase(userRepository = userRepository)

    @Provides
    @ViewModelScoped
    fun provideGetAuthenticateUserDetailUseCase(
        userRepository: IUserRepository
    ): GetAuthenticateUserDetailUseCase =
        GetAuthenticateUserDetailUseCase(userRepository = userRepository)


    @Provides
    @ViewModelScoped
    fun provideSaveCardUseCase(
        preferencesRepository: IPreferenceRepository,
        secureCardRepository: ISecureCardRepository,
        secureCardValidator: IBusinessEntityValidator<SecureCardBO>
    ): SaveCardUseCase =
        SaveCardUseCase(
            preferencesRepository = preferencesRepository,
            secureCardRepository = secureCardRepository,
            secureCardValidator = secureCardValidator
        )

    @Provides
    @ViewModelScoped
    fun provideGetAllCardsUseCase(
        preferenceRepository: IPreferenceRepository,
        secureCardRepository: ISecureCardRepository
    ): GetAllCardsUseCase =
        GetAllCardsUseCase(
            preferencesRepository = preferenceRepository,
            secureCardRepository = secureCardRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetCardByIdUseCase(
        preferenceRepository: IPreferenceRepository,
        secureCardRepository: ISecureCardRepository
    ): GetCardByIdUseCase =
        GetCardByIdUseCase(
            preferencesRepository = preferenceRepository,
            secureCardRepository = secureCardRepository
        )

    @Provides
    @ViewModelScoped
    fun provideRemoveCardUseCase(
        preferenceRepository: IPreferenceRepository,
        secureCardRepository: ISecureCardRepository
    ): RemoveSecureCardUseCase =
        RemoveSecureCardUseCase(
            preferenceRepository = preferenceRepository,
            secureCardRepository = secureCardRepository
        )

    @Provides
    @ViewModelScoped
    fun provideRemoveAllCardsUseCase(
        preferenceRepository: IPreferenceRepository,
        secureCardRepository: ISecureCardRepository
    ): RemoveAllCardsUseCase =
        RemoveAllCardsUseCase(
            preferencesRepository = preferenceRepository,
            secureCardRepository = secureCardRepository
        )

    @Provides
    @ViewModelScoped
    fun provideSaveAccountUseCase(
        preferencesRepository: IPreferenceRepository,
        accountRepository: IAccountRepository,
        accountValidator: IBusinessEntityValidator<AccountPasswordBO>
    ): SaveAccountUseCase =
        SaveAccountUseCase(
            preferencesRepository = preferencesRepository,
            accountRepository = accountRepository,
            accountValidator = accountValidator
        )

    @Provides
    @ViewModelScoped
    fun provideGetAllAccountsUseCase(
        accountRepository: IAccountRepository,
        preferenceRepository: IPreferenceRepository
    ): GetAllAccountsUseCase =
        GetAllAccountsUseCase(
            accountRepository = accountRepository,
            preferenceRepository = preferenceRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetAccountByIdUseCase(
        accountRepository: IAccountRepository,
        preferenceRepository: IPreferenceRepository
    ): GetAccountByIdUseCase =
        GetAccountByIdUseCase(
            accountRepository = accountRepository,
            preferenceRepository = preferenceRepository
        )

    @Provides
    @ViewModelScoped
    fun provideRemoveAccountUseCase(
        accountRepository: IAccountRepository,
        preferenceRepository: IPreferenceRepository
    ): RemovePasswordAccountUseCase =
        RemovePasswordAccountUseCase(
            accountRepository = accountRepository,
            preferenceRepository = preferenceRepository
        )

    @Provides
    @ViewModelScoped
    fun provideRemoveAllAccountsUseCase(
        accountRepository: IAccountRepository,
        preferenceRepository: IPreferenceRepository
    ): RemoveAllAccountsUseCase =
        RemoveAllAccountsUseCase(
            accountRepository = accountRepository,
            preferenceRepository = preferenceRepository
        )

    @Provides
    @ViewModelScoped
    fun provideRemoveAllCredentialsUseCase(
        accountRepository: IAccountRepository,
        secureCardRepository: ISecureCardRepository,
        preferenceRepository: IPreferenceRepository
    ): RemoveAllCredentialsUseCase = RemoveAllCredentialsUseCase(
        accountRepository = accountRepository,
        secureCardRepository = secureCardRepository,
        preferenceRepository = preferenceRepository
    )

    @Provides
    @ViewModelScoped
    fun provideSaveMasterKeyUseCaseUseCase(
        preferencesRepository: IPreferenceRepository,
        secretRepository: ISecretRepository,
        masterKeyValidator: IBusinessEntityValidator<SaveMasterKeyBO>
    ): SaveMasterKeyUseCase = SaveMasterKeyUseCase(
        preferencesRepository = preferencesRepository,
        secretRepository = secretRepository,
        masterKeyValidator = masterKeyValidator
    )

    @Provides
    @ViewModelScoped
    fun provideGetAllCredentialsUseCase(
        preferenceRepository: IPreferenceRepository,
        accountRepository: IAccountRepository,
        secureCardRepository: ISecureCardRepository
    ): GetAllCredentialsUseCase = GetAllCredentialsUseCase(
        preferenceRepository = preferenceRepository,
        accountRepository = accountRepository,
        secureCardRepository = secureCardRepository
    )
}