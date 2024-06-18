package com.dreamsoftware.vaultkeeper.utils

import com.dreamsoftware.brownie.utils.IBrownieApplicationAware

interface IVaultKeeperApplicationAware: IBrownieApplicationAware {

    fun isBiometricSupported(): Boolean

    fun isAccountUnlocked(): Boolean

    fun lockAccount()

    fun unlockAccount()
}