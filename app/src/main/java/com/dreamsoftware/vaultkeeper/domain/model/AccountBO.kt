package com.dreamsoftware.vaultkeeper.domain.model

import com.dreamsoftware.brownie.utils.EMPTY

data class AccountBO(
    override val uid: String,
    override val createdAt: Long,
    val accountName: String,
    val userName: String,
    val email: String,
    val mobileNumber: String,
    val password: String,
    val note: String,
    val userUid: String
): ICredentialBO, ICryptable<AccountBO> {
    companion object {
        const val FIELD_UID = "uid"
        const val FIELD_ACCOUNT_NAME = "accountName"
        const val FIELD_USER_NAME = "userName"
        const val FIELD_EMAIL = "email"
        const val FIELD_MOBILE_NUMBER = "mobileNumber"
        const val FIELD_PASSWORD = "password"
        const val FIELD_NOTE = "note"
        const val FIELD_CREATED_AT = "createdAt"
        const val FIELD_USER_UID = "userUid"
    }

    val displayInfo = when {
        email.isNotBlank() -> email
        userName.isNotBlank() -> userName
        mobileNumber.isNotBlank() -> mobileNumber
        else -> String.EMPTY
    }

    override fun accept(visitor: ICryptoVisitor): AccountBO =
        copy(
            accountName = visitor.visit(FIELD_ACCOUNT_NAME, accountName),
            userName = visitor.visit(FIELD_USER_NAME, userName),
            email = visitor.visit(FIELD_EMAIL, email),
            mobileNumber = visitor.visit(FIELD_MOBILE_NUMBER, mobileNumber),
            password = visitor.visit(FIELD_PASSWORD, password),
            note = visitor.visit(FIELD_NOTE, note)
        )
}
