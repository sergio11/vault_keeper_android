package com.dreamsoftware.vaultkeeper.di

import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.model.AuthRequestBO
import com.dreamsoftware.vaultkeeper.domain.model.SaveSecretBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.model.SignUpBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
import com.dreamsoftware.vaultkeeper.domain.usecase.CreateMasterKeyUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAccountByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllAccountsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllCredentialsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAuthenticateUserDetailUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetCardByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.LockAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllAccountsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllCredentialsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemovePasswordAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveSecureCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveCardUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignInUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignOffUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignUpUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.UnLockAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.UpdateBiometricAuthStateUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.UpdateMasterKeyUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.ValidateMasterKeyUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyBiometricAuthEnabledUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyUserAccountStatusUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyUserSessionUseCase
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
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
        preferenceRepository: IPreferenceRepository,
        applicationAware: IVaultKeeperApplicationAware
    ): SignOffUseCase =
        SignOffUseCase(
            userRepository = userRepository,
            preferenceRepository = preferenceRepository,
            applicationAware = applicationAware
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
    fun provideCreateMasterKeyUseCaseUseCase(
        preferencesRepository: IPreferenceRepository,
        secretRepository: ISecretRepository,
        masterKeyValidator: IBusinessEntityValidator<SaveSecretBO>,
        passwordGeneratorService: IPasswordGeneratorService,
        applicationAware: IVaultKeeperApplicationAware
    ): CreateMasterKeyUseCase = CreateMasterKeyUseCase(
        preferencesRepository = preferencesRepository,
        secretRepository = secretRepository,
        masterKeyValidator = masterKeyValidator,
        passwordGeneratorService = passwordGeneratorService,
        applicationAware = applicationAware
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

    @Provides
    @ViewModelScoped
    fun provideValidateMasterKeyUseCase(
        preferenceRepository: IPreferenceRepository,
        secretRepository: ISecretRepository,
        applicationAware: IVaultKeeperApplicationAware
    ): ValidateMasterKeyUseCase = ValidateMasterKeyUseCase(
        preferencesRepository = preferenceRepository,
        secretRepository = secretRepository,
        applicationAware = applicationAware
    )

    @Provides
    @ViewModelScoped
    fun provideLockAccountUseCase(
        applicationAware: IVaultKeeperApplicationAware
    ): LockAccountUseCase = LockAccountUseCase(
        applicationAware = applicationAware
    )

    @Provides
    @ViewModelScoped
    fun provideUnLockAccountUseCase(
        applicationAware: IVaultKeeperApplicationAware
    ): UnLockAccountUseCase = UnLockAccountUseCase(
        applicationAware = applicationAware
    )

    @Provides
    @ViewModelScoped
    fun provideUpdateBiometricAuthStateUseCase(
        preferenceRepository: IPreferenceRepository
    ): UpdateBiometricAuthStateUseCase = UpdateBiometricAuthStateUseCase(
        preferenceRepository = preferenceRepository
    )

    @Provides
    @ViewModelScoped
    fun provideVerifyBiometricAuthEnabledUseCase(
        preferenceRepository: IPreferenceRepository
    ): VerifyBiometricAuthEnabledUseCase = VerifyBiometricAuthEnabledUseCase(
        preferenceRepository = preferenceRepository
    )

    @Provides
    @ViewModelScoped
    fun provideVerifyUserAccountStatusUseCase(
        applicationAware: IVaultKeeperApplicationAware
    ): VerifyUserAccountStatusUseCase = VerifyUserAccountStatusUseCase(
        applicationAware = applicationAware
    )

    @Provides
    @ViewModelScoped
    fun provideUpdateMasterKeyUseCaseUseCase(
        preferencesRepository: IPreferenceRepository,
        secretRepository: ISecretRepository,
        masterKeyValidator: IBusinessEntityValidator<SaveSecretBO>,
        passwordGeneratorService: IPasswordGeneratorService,
        accountRepository: IAccountRepository,
        secureCardRepository: ISecureCardRepository
    ): UpdateMasterKeyUseCase = UpdateMasterKeyUseCase(
        preferencesRepository = preferencesRepository,
        secretRepository = secretRepository,
        masterKeyValidator = masterKeyValidator,
        passwordGeneratorService = passwordGeneratorService,
        accountRepository = accountRepository,
        secureCardRepository = secureCardRepository
    )
}