package com.dreamsoftware.vaultkeeper.domain.validation

interface IAccountValidationMessagesResolver {
    fun getAccountNameEmptyError(): String
    fun getUserNameEmptyError(): String
    fun getInvalidEmailError(): String
    fun getInvalidMobileNumberError(): String
    fun getInvalidPasswordError(): String
}