package com.dreamsoftware.vaultkeeper.ui.features.account.unlock

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface UnlockScreenActionListener: IBrownieScreenActionListener {
    fun onMaterKeyUpdated(newMasterKey: String)

    fun onValidate()

    fun onBiometricAuthSuccessfully()
}