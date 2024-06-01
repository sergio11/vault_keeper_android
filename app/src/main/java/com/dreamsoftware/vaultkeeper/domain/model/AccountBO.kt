package com.dreamsoftware.vaultkeeper.domain.model

data class AccountBO(
    val id: Int,
    val accountName: String,
    val userName: String,
    val email: String,
    val mobileNumber: String,
    val password: String,
    val note: String,
    val createdAt: Long
) {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_ACCOUNT_NAME = "accountName"
        const val FIELD_USER_NAME = "userName"
        const val FIELD_EMAIL = "email"
        const val FIELD_MOBILE_NUMBER = "mobileNumber"
        const val FIELD_PASSWORD = "password"
        const val FIELD_NOTE = "note"
        const val FIELD_CREATED_AT = "createdAt"
    }
}
