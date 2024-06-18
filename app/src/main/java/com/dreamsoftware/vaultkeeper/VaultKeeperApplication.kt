package com.dreamsoftware.vaultkeeper

import android.app.Application
import com.dreamsoftware.vaultkeeper.ui.utils.isBiometricSupported
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VaultKeeperApplication : Application(), IVaultKeeperApplicationAware {

    private companion object {
        var isAccountUnlocked: Boolean = false
    }

    override fun isBiometricSupported(): Boolean = applicationContext.isBiometricSupported()

    override fun isAccountUnlocked(): Boolean = isAccountUnlocked

    override fun lockAccount() {
        isAccountUnlocked = false
    }

    override fun unlockAccount() {
        isAccountUnlocked = true
    }
}