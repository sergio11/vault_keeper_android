package com.dreamsoftware.vaultkeeper.domain.validation

interface ISaveMasterKeyValidationMessagesResolver {
    fun getKeyEmptyError(): String
    fun getKeyMismatchError(): String
    fun getKeyIncorrectLengthError(): String
    fun getSaltIncorrectLengthError(): String
}