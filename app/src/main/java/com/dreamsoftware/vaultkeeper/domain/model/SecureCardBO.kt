package com.dreamsoftware.vaultkeeper.domain.model

data class SecureCardBO(
    override val id: Int,
    override val createdAt: Long,
    val cardHolderName: String,
    val cardNumber: String,
    val cardExpiryDate: String,
    val cardCvv: String,
    val cardProvider: String
): ICredentialBO {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_CARD_HOLDER_NAME = "cardHolderName"
        const val FIELD_CARD_NUMBER = "cardNumber"
        const val FIELD_CARD_EXPIRY_DATE = "cardExpiryDate"
        const val FIELD_CARD_CVV = "cardCvv"
        const val FIELD_CARD_PROVIDER = "cardProvider"
        const val FIELD_CREATED_AT = "createdAt"
    }
}
