package com.dreamsoftware.vaultkeeper.domain.validation.impl

import android.util.Patterns
import androidx.core.text.isDigitsOnly
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.validation.IAccountValidationMessagesResolver
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator

internal class AccountValidatorImpl(
    private val messagesResolver: IAccountValidationMessagesResolver
): IBusinessEntityValidator<AccountBO> {
    override fun validate(entity: AccountBO): Map<String, String> = buildMap {
        validateAccountName(entity)
        validateUserName(entity)
        validateEmail(entity)
        validatePassword(entity)
        validateMobileNumber(entity)
    }

    private fun MutableMap<String, String>.validateAccountName(entity: AccountBO) {
        if (entity.accountName.isBlank()) {
            put(AccountBO.FIELD_ACCOUNT_NAME, messagesResolver.getAccountNameEmptyError())
        }
    }

    private fun MutableMap<String, String>.validateUserName(entity: AccountBO) {
        if (entity.userName.isBlank()) {
            put(AccountBO.FIELD_USER_NAME, messagesResolver.getUserNameEmptyError())
        }
    }

    private fun MutableMap<String, String>.validateEmail(entity: AccountBO) {
        if (entity.email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(entity.email).matches()) {
            put(AccountBO.FIELD_EMAIL, messagesResolver.getInvalidEmailError())
        }
    }

    private fun MutableMap<String, String>.validatePassword(entity: AccountBO) {
        if(entity.password.isBlank() || entity.password.trim().isEmpty() || entity.password.contains("\\s+".toRegex())) {
            put(AccountBO.FIELD_PASSWORD, messagesResolver.getInvalidEmailError())
        }
    }

    private fun MutableMap<String, String>.validateMobileNumber(entity: AccountBO) {
        if (entity.mobileNumber.isBlank() || !entity.mobileNumber.isDigitsOnly()) {
            put(AccountBO.FIELD_MOBILE_NUMBER, messagesResolver.getInvalidMobileNumberError())
        }
    }
}