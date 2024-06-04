package com.dreamsoftware.vaultkeeper.data.remote.mapper

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.AuthUserDTO
import com.google.firebase.auth.FirebaseUser

class UserAuthenticatedMapper : IBrownieOneSideMapper<FirebaseUser, AuthUserDTO> {

    override fun mapInToOut(input: FirebaseUser): AuthUserDTO = with(input) {
        AuthUserDTO(
            uid = uid,
            displayName = displayName,
            email = email
        )
    }

    override fun mapInListToOutList(input: Iterable<FirebaseUser>): Iterable<AuthUserDTO> =
        input.map(::mapInToOut)
}