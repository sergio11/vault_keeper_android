package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO

internal class AccountLocalMapper: IBrownieMapper<AccountEntity, AccountPasswordBO> {
    override fun mapInToOut(input: AccountEntity): AccountPasswordBO = with(input) {
        AccountPasswordBO(
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

    override fun mapInListToOutList(input: Iterable<AccountEntity>): Iterable<AccountPasswordBO> =
        input.map(::mapInToOut)

    override fun mapOutListToInList(input: Iterable<AccountPasswordBO>): Iterable<AccountEntity> =
        input.map(::mapOutToIn)

    override fun mapOutToIn(input: AccountPasswordBO): AccountEntity = with(input) {
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