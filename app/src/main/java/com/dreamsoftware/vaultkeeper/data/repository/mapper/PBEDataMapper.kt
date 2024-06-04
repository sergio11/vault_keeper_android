package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO

class PBEDataMapper: IBrownieOneSideMapper<SecretDTO, PBEDataBO> {
    override fun mapInToOut(input: SecretDTO): PBEDataBO = with(input) {
        PBEDataBO(secret, salt)
    }

    override fun mapInListToOutList(input: Iterable<SecretDTO>): Iterable<PBEDataBO> =
        input.map(::mapInToOut)
}