package com.dreamsoftware.vaultkeeper.di

import android.content.Context
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.vaultkeeper.ui.features.account.signin.SignInScreenSimpleErrorMapper
import com.dreamsoftware.vaultkeeper.ui.features.account.signup.SignUpScreenSimpleErrorMapper
import com.dreamsoftware.vaultkeeper.ui.features.savecard.SaveSecureCardSimpleErrorMapper
import com.dreamsoftware.vaultkeeper.ui.features.savepassword.SavePasswordSimpleErrorMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    @Provides
    @ViewModelScoped
    @SignUpScreenErrorMapper
    fun provideSignUpScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        SignUpScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @SignInScreenErrorMapper
    fun provideSignInScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        SignInScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @SaveSecureCardErrorMapper
    fun provideSaveSecureCardSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        SaveSecureCardSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @SavePasswordErrorMapper
    fun provideSavePasswordSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        SavePasswordSimpleErrorMapper(context = context)
}