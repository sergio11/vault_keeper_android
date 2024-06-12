package com.dreamsoftware.vaultkeeper.domain.validation.impl

import android.util.Patterns
import androidx.core.text.isDigitsOnly
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.validation.IAccountValidationMessagesResolver
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator

internal class AccountValidatorImpl(
    private val messagesResolver: IAccountValidationMessagesResolver
): IBusinessEntityValidator<AccountPasswordBO> {
    override fun validate(entity: AccountPasswordBO): Map<String, String> = buildMap {
        validateAccountName(entity)
        validateUserName(entity)
        validateEmail(entity)
        validatePassword(entity)
        validateMobileNumber(entity)
    }

    private fun MutableMap<String, String>.validateAccountName(entity: AccountPasswordBO) {
        if (entity.accountName.isBlank()) {
            put(AccountPasswordBO.FIELD_ACCOUNT_NAME, messagesResolver.getAccountNameEmptyError())
        }
    }

    private fun MutableMap<String, String>.validateUserName(entity: AccountPasswordBO) {
        if (entity.userName.isBlank()) {
            put(AccountPasswordBO.FIELD_USER_NAME, messagesResolver.getUserNameEmptyError())
        }
    }

    private fun MutableMap<String, String>.validateEmail(entity: AccountPasswordBO) {
        if (entity.email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(entity.email).matches()) {
            put(AccountPasswordBO.FIELD_EMAIL, messagesResolver.getInvalidEmailError())
        }
    }

    private fun MutableMap<String, String>.validatePassword(entity: AccountPasswordBO) {
        if(entity.password.isBlank() || entity.password.trim().isEmpty() || entity.password.contains("\\s+".toRegex())) {
            put(AccountPasswordBO.FIELD_PASSWORD, messagesResolver.getInvalidEmailError())
        }
    }

    private fun MutableMap<String, String>.validateMobileNumber(entity: AccountPasswordBO) {
        if (entity.mobileNumber.isBlank() || !entity.mobileNumber.isDigitsOnly()) {
            put(AccountPasswordBO.FIELD_MOBILE_NUMBER, messagesResolver.getInvalidMobileNumberError())
        }
    }
}