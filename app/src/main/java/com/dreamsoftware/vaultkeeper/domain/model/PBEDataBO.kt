package com.dreamsoftware.vaultkeeper.domain.model

data class PBEDataBO(
    val secret: String,
    val salt: String
): ICryptable<PBEDataBO> {

    companion object {
        const val FIELD_SECRET = "secret"
        const val FIELD_SALT = "salt"
    }

    override fun accept(visitor: ICryptoVisitor): PBEDataBO = with(visitor) {
        copy(
            secret = visit(FIELD_SECRET, secret),
            salt = visit(FIELD_SALT, salt)
        )
    }
}