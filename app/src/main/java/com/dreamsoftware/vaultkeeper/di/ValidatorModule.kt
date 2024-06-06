package com.dreamsoftware.vaultkeeper.di

import android.content.Context
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.model.AuthRequestBO
import com.dreamsoftware.vaultkeeper.domain.model.SaveMasterKeyBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.model.SignUpBO
import com.dreamsoftware.vaultkeeper.domain.validation.IAccountValidationMessagesResolver
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.domain.validation.ISaveMasterKeyValidationMessagesResolver
import com.dreamsoftware.vaultkeeper.domain.validation.ISecureCardValidationMessagesResolver
import com.dreamsoftware.vaultkeeper.domain.validation.ISignInValidationMessagesResolver
import com.dreamsoftware.vaultkeeper.domain.validation.ISignUpValidationMessagesResolver
import com.dreamsoftware.vaultkeeper.domain.validation.impl.AccountValidatorImpl
import com.dreamsoftware.vaultkeeper.domain.validation.impl.SaveMasterKeyValidatorImpl
import com.dreamsoftware.vaultkeeper.domain.validation.impl.SecureCardValidatorImpl
import com.dreamsoftware.vaultkeeper.domain.validation.impl.SignInValidatorImpl
import com.dreamsoftware.vaultkeeper.domain.validation.impl.SignUpValidatorImpl
import com.dreamsoftware.vaultkeeper.ui.validation.AccountValidationMessagesResolverImpl
import com.dreamsoftware.vaultkeeper.ui.validation.SaveMasterKeyValidationMessagesResolverImpl
import com.dreamsoftware.vaultkeeper.ui.validation.SecureCardValidationMessagesResolverImpl
import com.dreamsoftware.vaultkeeper.ui.validation.SignInValidationMessagesResolverImpl
import com.dreamsoftware.vaultkeeper.ui.validation.SignUpValidationMessagesResolverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ValidatorModule {

    @Provides
    @ViewModelScoped
    fun provideSecureCardValidationMessagesResolver(
        @ApplicationContext context: Context
    ): ISecureCardValidationMessagesResolver = SecureCardValidationMessagesResolverImpl(context)

    @Provides
    @ViewModelScoped
    fun provideAccountValidationMessagesResolver(
        @ApplicationContext context: Context
    ): IAccountValidationMessagesResolver = AccountValidationMessagesResolverImpl(context)

    @Provides
    @ViewModelScoped
    fun provideSaveMasterKeyValidationMessagesResolver(
        @ApplicationContext context: Context
    ): ISaveMasterKeyValidationMessagesResolver = SaveMasterKeyValidationMessagesResolverImpl(context)

    @Provides
    @ViewModelScoped
    fun provideSignUpValidationMessagesResolver(
        @ApplicationContext context: Context
    ): ISignUpValidationMessagesResolver = SignUpValidationMessagesResolverImpl(context)

    @Provides
    @ViewModelScoped
    fun provideSignInValidationMessagesResolver(
        @ApplicationContext context: Context
    ): ISignInValidationMessagesResolver = SignInValidationMessagesResolverImpl(context)

    
    @Provides
    @ViewModelScoped
    fun provideSecureCardValidator(
        messagesResolver: ISecureCardValidationMessagesResolver
    ): IBusinessEntityValidator<SecureCardBO> = SecureCardValidatorImpl(messagesResolver)

    @Provides
    @ViewModelScoped
    fun provideAccountValidator(
        messagesResolver: IAccountValidationMessagesResolver
    ): IBusinessEntityValidator<AccountBO> = AccountValidatorImpl(messagesResolver)

    @Provides
    @ViewModelScoped
    fun provideSaveMasterKeyValidator(
        messagesResolver: ISaveMasterKeyValidationMessagesResolver
    ): IBusinessEntityValidator<SaveMasterKeyBO> = SaveMasterKeyValidatorImpl(messagesResolver)

    @Provides
    @ViewModelScoped
    fun provideSignUpValidator(
        messagesResolver: ISignUpValidationMessagesResolver
    ): IBusinessEntityValidator<SignUpBO> = SignUpValidatorImpl(messagesResolver)

    @Provides
    @ViewModelScoped
    fun provideSignInValidator(
        messagesResolver: ISignInValidationMessagesResolver
    ): IBusinessEntityValidator<AuthRequestBO> = SignInValidatorImpl(messagesResolver)
}