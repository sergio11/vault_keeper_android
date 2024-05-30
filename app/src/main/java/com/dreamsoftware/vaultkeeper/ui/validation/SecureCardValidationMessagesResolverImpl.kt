package com.dreamsoftware.vaultkeeper.ui.validation

import android.content.Context
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.validation.ISecureCardValidationMessagesResolver

internal class SecureCardValidationMessagesResolverImpl(
    private val context: Context
) : ISecureCardValidationMessagesResolver {
    override fun getCardProviderError(): String = context.getString(R.string.error_card_provider)

    override fun getCardNumberEmptyError(): String =
        context.getString(R.string.error_card_number_empty)

    override fun getCardNumberLengthError(): String =
        context.getString(R.string.error_card_number_length)

    override fun getCardNumberInvalidError(): String =
        context.getString(R.string.error_card_number_invalid)

    override fun getCardHolderNameEmptyError(): String =
        context.getString(R.string.error_card_holder_name_empty)

    override fun getCardCvvEmptyError(): String = context.getString(R.string.error_card_cvv_empty)

    override fun getCardCvvInvalidError(): String =
        context.getString(R.string.error_card_cvv_invalid)

    override fun getCardCvvLengthError(): String = context.getString(R.string.error_card_cvv_length)

    override fun getCardExpiryDateEmptyError(): String =
        context.getString(R.string.error_card_expiry_date_empty)

    override fun getCardExpiryDateInvalidError(): String =
        context.getString(R.string.error_card_expiry_date_invalid)
}