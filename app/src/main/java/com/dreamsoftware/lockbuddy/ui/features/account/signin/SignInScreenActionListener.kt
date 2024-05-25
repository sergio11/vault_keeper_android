package com.dreamsoftware.lockbuddy.ui.features.account.signin

interface SignInScreenActionListener {
    fun onEmailChanged(newEmail: String)
    fun onPasswordChanged(newPassword: String)
    fun onSignIn()
}