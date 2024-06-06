package com.dreamsoftware.vaultkeeper.domain.validation

interface ISignInValidationMessagesResolver {
    fun getInvalidEmailMessage(): String
    fun getShortPasswordMessage(minLength: Int): String
}