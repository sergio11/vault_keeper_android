package com.dreamsoftware.vaultkeeper.ui.validation

import android.content.Context
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.validation.IAccountValidationMessagesResolver

internal class AccountValidationMessagesResolverImpl(
    private val context: Context
) : IAccountValidationMessagesResolver {
    override fun getAccountNameEmptyError(): String = context.getString(R.string.invalid_account_name)

    override fun getUserNameEmptyError(): String = context.getString(R.string.invalid_user_name)

    override fun getInvalidEmailError(): String = context.getString(R.string.invalid_email_address)

    override fun getInvalidMobileNumberError(): String = context.getString(R.string.invalid_mobile_number)

    override fun getInvalidPasswordError(): String = context.getString(R.string.invalid_password)
}