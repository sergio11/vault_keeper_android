package com.dreamsoftware.vaultkeeper.ui.features.account.createmasterkey

interface CreateMasterKeyScreenActionListener {
    fun onMaterKeyUpdated(newMasterKey: String)
    fun onRepeatMasterKeyUpdated(newRepeatMasterKey: String)
    fun onSave()
}