package com.dreamsoftware.vaultkeeper.ui.features.unlock

interface UnlockScreenActionListener {
    fun onMaterKeyUpdated(newMasterKey: String)

    fun onValidate()
}