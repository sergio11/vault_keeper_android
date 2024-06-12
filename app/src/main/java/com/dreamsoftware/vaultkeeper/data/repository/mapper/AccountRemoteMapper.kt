package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.AccountDTO
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO

internal class AccountRemoteMapper: IBrownieMapper<AccountDTO, AccountPasswordBO> {
    override fun mapInToOut(input: AccountDTO): AccountPasswordBO = with(input) {
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

    override fun mapInListToOutList(input: Iterable<AccountDTO>): Iterable<AccountPasswordBO> =
        input.map(::mapInToOut)

    override fun mapOutListToInList(input: Iterable<AccountPasswordBO>): Iterable<AccountDTO> =
        input.map(::mapOutToIn)

    override fun mapOutToIn(input: AccountPasswordBO): AccountDTO = with(input) {
        AccountDTO(
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