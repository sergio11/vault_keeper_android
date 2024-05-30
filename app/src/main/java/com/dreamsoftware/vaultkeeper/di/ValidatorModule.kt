package com.dreamsoftware.vaultkeeper.di

import android.content.Context
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.domain.validation.ISecureCardValidationMessagesResolver
import com.dreamsoftware.vaultkeeper.domain.validation.impl.SecureCardValidatorImpl
import com.dreamsoftware.vaultkeeper.ui.validation.SecureCardValidationMessagesResolverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class ValidatorModule {

    @Provides
    @Singleton
    fun provideSecureCardValidationMessagesResolver(
        @ApplicationContext context: Context
    ): ISecureCardValidationMessagesResolver = SecureCardValidationMessagesResolverImpl(context)

    @Provides
    @Singleton
    fun provideSecureCardValidator(
        messagesResolver: ISecureCardValidationMessagesResolver
    ): IBusinessEntityValidator<SecureCardBO> = SecureCardValidatorImpl(messagesResolver)

}