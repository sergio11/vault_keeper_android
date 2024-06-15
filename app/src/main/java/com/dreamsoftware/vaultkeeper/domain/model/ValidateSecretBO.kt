package com.dreamsoftware.vaultkeeper.domain.model

data class ValidateSecretBO(
    val key: String,
    val userUid: String
) {
    companion object {
        const val FIELD_KEY = "key"
        const val FIELD_USER_UID = "userUid"
    }
}