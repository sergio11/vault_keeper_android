package com.dreamsoftware.vaultkeeper.ui.features.account.onboarding

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface OnboardingScreenActionListener: IBrownieScreenActionListener {
    fun onNavigateToSignIn()
    fun onNavigateToSignUp()
}