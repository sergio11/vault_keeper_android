package com.dreamsoftware.vaultkeeper.domain.validation

interface ISecureCardValidationMessagesResolver {
    fun getCardProviderError(): String
    fun getCardNumberEmptyError(): String
    fun getCardNumberLengthError(): String
    fun getCardNumberInvalidError(): String
    fun getCardHolderNameEmptyError(): String
    fun getCardCvvEmptyError(): String
    fun getCardCvvInvalidError(): String
    fun getCardCvvLengthError(): String
    fun getCardExpiryDateEmptyError(): String
    fun getCardExpiryDateInvalidError(): String
}