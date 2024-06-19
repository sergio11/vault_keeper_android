package com.dreamsoftware.vaultkeeper.ui.features.account.createmasterkey

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface CreateMasterKeyScreenActionListener: IBrownieScreenActionListener {
    fun onMaterKeyUpdated(newMasterKey: String)
    fun onRepeatMasterKeyUpdated(newRepeatMasterKey: String)
    fun onSave()
}