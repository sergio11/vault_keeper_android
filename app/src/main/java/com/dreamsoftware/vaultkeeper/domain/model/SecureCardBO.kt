package com.dreamsoftware.vaultkeeper.domain.model

data class SecureCardBO(
    override val uid: String,
    override val createdAt: Long,
    val cardHolderName: String,
    val cardNumber: String,
    val cardExpiryDate: String,
    val cardCvv: String,
    val cardProvider: CardProviderEnum,
    val userUid: String
): ICredentialBO, ICryptable<SecureCardBO> {
    companion object {
        const val FIELD_UID = "uid"
        const val FIELD_CARD_HOLDER_NAME = "cardHolderName"
        const val FIELD_CARD_NUMBER = "cardNumber"
        const val FIELD_CARD_EXPIRY_DATE = "cardExpiryDate"
        const val FIELD_CARD_CVV = "cardCvv"
        const val FIELD_CARD_PROVIDER = "cardProvider"
        const val FIELD_USER_UID = "userUid"
        const val FIELD_CREATED_AT = "createdAt"
    }

    override fun accept(visitor: ICryptoVisitor): SecureCardBO = with(visitor) {
        copy(
            cardHolderName = visit(FIELD_CARD_HOLDER_NAME, cardHolderName),
            cardNumber = visit(FIELD_CARD_NUMBER, cardNumber),
            cardExpiryDate = visit(FIELD_CARD_EXPIRY_DATE, cardExpiryDate),
            cardCvv = visit(FIELD_CARD_CVV, cardCvv),
        )
    }
}
