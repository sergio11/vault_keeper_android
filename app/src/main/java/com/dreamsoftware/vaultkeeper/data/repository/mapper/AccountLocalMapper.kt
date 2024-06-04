package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO

internal class AccountLocalMapper: IBrownieMapper<AccountEntity, AccountBO> {
    override fun mapInToOut(input: AccountEntity): AccountBO = with(input) {
        AccountBO(
            uid = uid,
            accountName = accountName,
            userName = userName,
            email = email,
            mobileNumber = mobileNumber,
            password = password,
            note = note,
            createdAt = createdAt,
            userUid = userUid
        )
    }

    override fun mapInListToOutList(input: Iterable<AccountEntity>): Iterable<AccountBO> =
        input.map(::mapInToOut)

    override fun mapOutListToInList(input: Iterable<AccountBO>): Iterable<AccountEntity> =
        input.map(::mapOutToIn)

    override fun mapOutToIn(input: AccountBO): AccountEntity = with(input) {
        AccountEntity(
            uid = uid,
            accountName = accountName,
            userName = userName,
            email = email,
            mobileNumber = mobileNumber,
            password = password,
            note = note,
            createdAt = createdAt,
            userUid = userUid
        )
    }
}