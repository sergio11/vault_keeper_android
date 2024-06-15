package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.domain.model.SaveSecretBO

class SaveSecretMapper: IBrownieOneSideMapper<SaveSecretBO, SecretDTO> {
    override fun mapInToOut(input: SaveSecretBO): SecretDTO = with(input) {
        SecretDTO(
            userUid = userUid,
            secret = key,
            salt = salt
        )
    }

    override fun mapInListToOutList(input: Iterable<SaveSecretBO>): Iterable<SecretDTO> =
        input.map(::mapInToOut)
}