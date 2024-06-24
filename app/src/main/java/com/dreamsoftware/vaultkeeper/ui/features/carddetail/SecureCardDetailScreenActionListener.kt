package com.dreamsoftware.vaultkeeper.ui.features.carddetail

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface SecureCardDetailScreenActionListener: IBrownieScreenActionListener {
    fun onCancel()
    fun onDeleteSecureCard()
    fun onDeleteSecureCardConfirmed()
    fun onDeleteSecureCardCancelled()
    fun onEditSecureCard()
}