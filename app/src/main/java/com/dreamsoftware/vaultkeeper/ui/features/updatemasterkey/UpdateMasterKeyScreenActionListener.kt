package com.dreamsoftware.vaultkeeper.ui.features.updatemasterkey

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface UpdateMasterKeyScreenActionListener: IBrownieScreenActionListener {
    fun onMaterKeyUpdated(newMasterKey: String)
    fun onRepeatMasterKeyUpdated(newRepeatMasterKey: String)
    fun onSave()
}