package com.dreamsoftware.vaultkeeper.ui.features.accountpassworddetail

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface AccountPasswordDetailScreenActionListener: IBrownieScreenActionListener {
    fun onCancel()
    fun onDeleteAccount()
    fun onDeleteAccountConfirmed()
    fun onDeleteAccountCancelled()
    fun onEditAccountPassword()
}