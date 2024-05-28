package com.dreamsoftware.vaultkeeper.di

import android.content.Context
import com.dreamsoftware.vaultkeeper.ui.features.account.signin.SignInScreenSimpleErrorMapper
import com.dreamsoftware.vaultkeeper.ui.features.account.signup.SignUpScreenSimpleErrorMapper
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
    fun provideSignUpScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): SignUpScreenSimpleErrorMapper =
        SignUpScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    fun provideSignInScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): SignInScreenSimpleErrorMapper =
        SignInScreenSimpleErrorMapper(context = context)
}