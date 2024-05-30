package com.dreamsoftware.vaultkeeper.ui.features.savecard

interface SaveCardScreenActionListener {
    fun onSaveSecureCard()
    fun onCardNumberUpdated(newCardNumber: String)
    fun onCardHolderNameUpdated(newCardHolderName: String)
    fun onCardExpiryDateUpdated(newCardExpiryDate: String)
    fun onCardCvvUpdated(newCardCvv: String)
    fun onExpandedProviderFieldUpdated(isExpanded: Boolean)
    fun onCardProviderUpdated(cardProviderName: String, selectedCardImage: Int)
}