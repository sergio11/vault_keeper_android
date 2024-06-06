package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.AuthUserDTO
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO

internal class AuthUserMapper: IBrownieOneSideMapper<AuthUserInfo, AuthUserBO> {
    override fun mapInToOut(input: AuthUserInfo): AuthUserBO = with(input) {
        AuthUserBO(
            uid = authData.uid,
            displayName = authData.displayName.orEmpty(),
            email = authData.email.orEmpty(),
            photoUrl = authData.photoUrl.orEmpty(),
            hasMasterKey = hasMasterKey
        )
    }

    override fun mapInListToOutList(input: Iterable<AuthUserInfo>): Iterable<AuthUserBO> =
        input.map(::mapInToOut)
}

data class AuthUserInfo(
    val authData: AuthUserDTO,
    val hasMasterKey: Boolean
)