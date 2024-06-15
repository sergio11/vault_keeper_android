package com.dreamsoftware.vaultkeeper.domain.model

data class SaveSecretBO(
    val key: String,
    val confirmKey: String,
    val salt: String,
    val userUid: String
): ICryptable<SaveSecretBO> {
    companion object {
        const val FIELD_KEY = "key"
        const val FIELD_CONFIRM_KEY = "confirmKey"
        const val FIELD_SALT_KEY = "salt"
        const val FIELD_USER_UID = "userUid"
    }

    override fun accept(visitor: ICryptoVisitor): SaveSecretBO = with(visitor) {
        copy(
            key = visit(FIELD_KEY, key),
            confirmKey = visit(FIELD_CONFIRM_KEY, confirmKey),
            salt = visit(FIELD_SALT_KEY, salt),
            userUid = visit(FIELD_USER_UID, userUid)
        )
    }
}