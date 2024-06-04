package com.dreamsoftware.vaultkeeper.data.remote.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import java.util.Date

class SecureCardMapper : IBrownieMapper<SecureCardDTO, Map<String, Any?>> {

    private companion object {
        const val CARD_UUID = "uid"
        const val CARD_HOLDER_NAME_KEY = "cardHolderName"
        const val CARD_NUMBER_KEY = "cardNumber"
        const val CARD_EXPIRY_DATE_KEY = "cardExpiryDate"
        const val CARD_CVV_KEY = "cardCvv"
        const val CARD_PROVIDER_KEY = "cardProvider"
        const val CREATED_AT_KEY = "createdAt"
        const val CARD_USER_UID_KEY = "userUid"
    }

    override fun mapInToOut(input: SecureCardDTO): Map<String, Any?> = with(input) {
        hashMapOf(
            CARD_UUID to uid,
            CARD_HOLDER_NAME_KEY to cardHolderName,
            CARD_NUMBER_KEY to cardNumber,
            CARD_EXPIRY_DATE_KEY to cardExpiryDate,
            CARD_CVV_KEY to cardCvv,
            CARD_PROVIDER_KEY to cardProvider,
            CREATED_AT_KEY to Date().time.toString(),
            CARD_USER_UID_KEY to userUid
        )
    }

    override fun mapInListToOutList(input: Iterable<SecureCardDTO>): Iterable<Map<String, Any?>> =
        input.map(::mapInToOut)

    override fun mapOutToIn(input: Map<String, Any?>): SecureCardDTO = with(input) {
        SecureCardDTO(
            uid = get(CARD_UUID) as String,
            cardHolderName = get(CARD_HOLDER_NAME_KEY) as String,
            cardNumber = get(CARD_NUMBER_KEY) as String,
            cardExpiryDate = get(CARD_EXPIRY_DATE_KEY) as String,
            cardCvv = get(CARD_CVV_KEY) as String,
            cardProvider = get(CARD_PROVIDER_KEY) as String,
            createdAt = (get(CREATED_AT_KEY) as String).toLong(),
            userUid = get(CARD_USER_UID_KEY) as String
        )
    }

    override fun mapOutListToInList(input: Iterable<Map<String, Any?>>): Iterable<SecureCardDTO> =
        input.map(::mapOutToIn)
}