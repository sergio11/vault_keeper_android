package com.dreamsoftware.vaultkeeper.domain.validation.impl

import androidx.core.text.isDigitsOnly
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.domain.validation.ISecureCardValidationMessagesResolver
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

internal class SecureCardValidatorImpl(
    private val messagesResolver: ISecureCardValidationMessagesResolver
): IBusinessEntityValidator<SecureCardBO> {
    override fun validate(entity: SecureCardBO): Map<String, String> = buildMap {
        validateCardNumber(entity)
        validateCardHolderName(entity)
        validateCardCvv(entity)
        validateCardExpiryDate(entity)
    }

    private fun MutableMap<String, String>.validateCardNumber(entity: SecureCardBO) {
        when {
            entity.cardNumber.isEmpty() -> {
                put(SecureCardBO.FIELD_CARD_NUMBER, messagesResolver.getCardNumberEmptyError())
            }
            entity.cardNumber.length < 16 -> {
                put(SecureCardBO.FIELD_CARD_NUMBER, messagesResolver.getCardNumberLengthError())
            }
            !entity.cardNumber.isDigitsOnly() -> {
                put(SecureCardBO.FIELD_CARD_NUMBER, messagesResolver.getCardNumberInvalidError())
            }
        }
    }

    private fun MutableMap<String, String>.validateCardHolderName(entity: SecureCardBO) {
        if (entity.cardHolderName.isEmpty()) {
            put(SecureCardBO.FIELD_CARD_HOLDER_NAME, messagesResolver.getCardHolderNameEmptyError())
        }
    }

    private fun MutableMap<String, String>.validateCardCvv(entity: SecureCardBO) {
        when {
            entity.cardCvv.isEmpty() -> {
                put(SecureCardBO.FIELD_CARD_CVV, messagesResolver.getCardCvvEmptyError())
            }
            !entity.cardCvv.isDigitsOnly() -> {
                put(SecureCardBO.FIELD_CARD_CVV, messagesResolver.getCardCvvInvalidError())
            }
            entity.cardCvv.length < 3 -> {
                put(SecureCardBO.FIELD_CARD_CVV, messagesResolver.getCardCvvLengthError())
            }
        }
    }

    private fun MutableMap<String, String>.validateCardExpiryDate(entity: SecureCardBO) {
        if (entity.cardExpiryDate.isEmpty()) {
            put(SecureCardBO.FIELD_CARD_EXPIRY_DATE, messagesResolver.getCardExpiryDateEmptyError())
        } else {
            if (!validateExpiryDate(entity.cardExpiryDate)) {
                put(SecureCardBO.FIELD_CARD_EXPIRY_DATE, messagesResolver.getCardExpiryDateInvalidError())
            }
        }
    }

    private fun validateExpiryDate(cardExpiryDate: String): Boolean {
        val currentDate = YearMonth.now()
        val expiryDate: YearMonth = try {
            YearMonth.parse(cardExpiryDate, DateTimeFormatter.ofPattern("MMyy"))
        } catch (e: DateTimeParseException) {
            return false
        }
        return !expiryDate.isBefore(currentDate)
    }
}