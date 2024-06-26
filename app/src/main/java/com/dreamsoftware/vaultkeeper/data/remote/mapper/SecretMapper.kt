package com.dreamsoftware.vaultkeeper.data.remote.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO

class SecretMapper : IBrownieMapper<SecretDTO, Map<String, Any?>> {

    private companion object {
        const val SECRET_KEY = "secret"
        const val SALT_KEY = "salt"
        const val UID_KEY = "userUid"
    }

    override fun mapInToOut(input: SecretDTO): Map<String, Any?> = with(input) {
        hashMapOf(
            UID_KEY to userUid,
            SECRET_KEY to secret,
            SALT_KEY to salt
        )
    }

    override fun mapInListToOutList(input: Iterable<SecretDTO>): Iterable<Map<String, Any?>> =
        input.map(::mapInToOut)

    override fun mapOutToIn(input: Map<String, Any?>): SecretDTO = with(input) {
        SecretDTO(
            userUid = get(UID_KEY) as String,
            secret = get(SECRET_KEY) as String,
            salt = get(SALT_KEY) as String
        )
    }

    override fun mapOutListToInList(input: Iterable<Map<String, Any?>>): Iterable<SecretDTO> =
        input.map(::mapOutToIn)
}