package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.AccountDTO
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO

internal class AccountRemoteMapper: IBrownieMapper<AccountDTO, AccountBO> {
    override fun mapInToOut(input: AccountDTO): AccountBO = with(input) {
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

    override fun mapInListToOutList(input: Iterable<AccountDTO>): Iterable<AccountBO> =
        input.map(::mapInToOut)

    override fun mapOutListToInList(input: Iterable<AccountBO>): Iterable<AccountDTO> =
        input.map(::mapOutToIn)

    override fun mapOutToIn(input: AccountBO): AccountDTO = with(input) {
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