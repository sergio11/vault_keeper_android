package com.dreamsoftware.vaultkeeper.domain.model

data class SaveMasterKeyBO(
    val key: String,
    val confirmKey: String,
    val userUid: String
) {
    companion object {
        const val FIELD_KEY = "key"
        const val FIELD_CONFIRM_KEY = "confirmKey"
        const val FIELD_USER_UID = "userUid"
    }
}