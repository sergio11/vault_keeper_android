package com.dreamsoftware.vaultkeeper.ui.features.account.signin

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface SignInScreenActionListener: IBrownieScreenActionListener {
    fun onEmailChanged(newEmail: String)
    fun onPasswordChanged(newPassword: String)
    fun onSignIn()
}