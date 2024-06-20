package com.dreamsoftware.vaultkeeper.ui.features.updatemasterkey

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface UpdateMasterKeyScreenActionListener: IBrownieScreenActionListener {
    fun onMasterKeyUpdated(masterKey: String)
    fun onNewMasterKeyUpdated(newMasterKey: String)
    fun onRepeatNewMasterKeyUpdated(newRepeatMasterKey: String)
    fun onSave()
}