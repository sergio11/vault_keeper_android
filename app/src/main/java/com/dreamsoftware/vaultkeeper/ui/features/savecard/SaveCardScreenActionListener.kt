package com.dreamsoftware.vaultkeeper.ui.features.savecard

import com.dreamsoftware.brownie.component.BrownieDropdownMenuItem
import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface SaveCardScreenActionListener: IBrownieScreenActionListener {
    fun onSaveSecureCard()
    fun onCancel()
    fun onCardNumberUpdated(newCardNumber: String)
    fun onCardHolderNameUpdated(newCardHolderName: String)
    fun onCardExpiryDateUpdated(newCardExpiryDate: String)
    fun onCardCvvUpdated(newCardCvv: String)
    fun onCardProviderUpdated(cardProvider: BrownieDropdownMenuItem)
}