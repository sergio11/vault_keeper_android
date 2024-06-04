package com.dreamsoftware.vaultkeeper.data.remote.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.AccountDTO
import java.util.Date

class AccountMapper : IBrownieMapper<AccountDTO, Map<String, Any?>> {

    private companion object {
        const val ACCOUNT_UUID = "uid"
        const val ACCOUNT_NAME_KEY = "accountName"
        const val USERNAME_KEY = "userName"
        const val MOBILE_NUMBER_KEY = "mobileNumber"
        const val PASSWORD_KEY = "password"
        const val NOTE_KEY = "note"
        const val ACCOUNT_EMAIL_KEY = "email"
        const val CREATED_AT_KEY = "createdAt"
        const val ACCOUNT_USER_UID_KEY = "userUid"
    }

    override fun mapInToOut(input: AccountDTO): Map<String, Any?> = with(input) {
        hashMapOf(
            ACCOUNT_UUID to uid,
            ACCOUNT_NAME_KEY to accountName,
            USERNAME_KEY to userName,
            MOBILE_NUMBER_KEY to mobileNumber,
            PASSWORD_KEY to password,
            NOTE_KEY to note,
            ACCOUNT_EMAIL_KEY to email,
            CREATED_AT_KEY to Date().time.toString(),
            ACCOUNT_USER_UID_KEY to userUid
        )
    }

    override fun mapInListToOutList(input: Iterable<AccountDTO>): Iterable<Map<String, Any?>> =
        input.map(::mapInToOut)

    override fun mapOutToIn(input: Map<String, Any?>): AccountDTO = with(input) {
        AccountDTO(
            uid = get(ACCOUNT_UUID) as String,
            accountName = get(ACCOUNT_NAME_KEY) as String,
            userName = get(USERNAME_KEY) as String,
            mobileNumber = get(MOBILE_NUMBER_KEY) as String,
            password = get(PASSWORD_KEY) as String,
            note = get(NOTE_KEY) as String,
            email = get(ACCOUNT_EMAIL_KEY) as String,
            createdAt = (get(CREATED_AT_KEY) as String).toLong(),
            userUid = get(ACCOUNT_USER_UID_KEY) as String
        )
    }

    override fun mapOutListToInList(input: Iterable<Map<String, Any?>>): Iterable<AccountDTO> =
        input.map(::mapOutToIn)
}