package com.dreamsoftware.vaultkeeper

import android.app.Application
import com.dreamsoftware.vaultkeeper.ui.utils.isBiometricSupported
import com.dreamsoftware.vaultkeeper.utils.IApplicationAware
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VaultKeeperApplication : Application(), IApplicationAware {
    override fun isBiometricSupported(): Boolean = applicationContext.isBiometricSupported()
}