package com.dreamsoftware.vaultkeeper.domain.model

import com.dreamsoftware.brownie.utils.EMPTY

data class AccountBO(
    override val id: Int,
    override val createdAt: Long,
    val accountName: String,
    val userName: String,
    val email: String,
    val mobileNumber: String,
    val password: String,
    val note: String
): ICredentialBO {
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
    val displayInfo = when {
        email.isNotBlank() -> email
        userName.isNotBlank() -> userName
        mobileNumber.isNotBlank() -> mobileNumber
        else -> String.EMPTY
    }
}
