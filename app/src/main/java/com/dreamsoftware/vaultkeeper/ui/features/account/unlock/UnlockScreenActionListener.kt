package com.dreamsoftware.vaultkeeper.ui.features.account.unlock

interface UnlockScreenActionListener {
    fun onMaterKeyUpdated(newMasterKey: String)

    fun onValidate()
}