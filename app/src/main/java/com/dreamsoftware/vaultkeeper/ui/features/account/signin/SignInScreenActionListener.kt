package com.dreamsoftware.vaultkeeper.ui.features.account.signin

interface SignInScreenActionListener {
    fun onEmailChanged(newEmail: String)
    fun onPasswordChanged(newPassword: String)
    fun onSignIn()
}