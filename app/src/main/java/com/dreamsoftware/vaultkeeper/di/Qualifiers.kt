package com.dreamsoftware.vaultkeeper.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainImmediateDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SaveSecureCardErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccountPasswordDetailErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SecureCardDetailErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SavePasswordErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SignUpScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SignInScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CreateMasterKeyErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UpdateMasterKeyErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HomeErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnlockScreenErrorMapper