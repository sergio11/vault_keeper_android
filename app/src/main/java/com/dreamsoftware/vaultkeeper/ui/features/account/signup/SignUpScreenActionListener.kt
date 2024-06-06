package com.dreamsoftware.vaultkeeper.ui.features.account.signup

interface SignUpScreenActionListener {
    fun onEmailChanged(newEmail: String)
    fun onPasswordChanged(newPassword: String)
    fun onConfirmPasswordChanged(newConfirmPassword: String)
    fun onSignUp()
    fun onNavigateToSignIn()
}