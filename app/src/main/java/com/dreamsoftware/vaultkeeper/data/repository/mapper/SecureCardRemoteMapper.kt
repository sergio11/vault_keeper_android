package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecureCardDTO
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO

internal class SecureCardRemoteMapper: IBrownieMapper<SecureCardDTO, SecureCardBO> {
    override fun mapInToOut(input: SecureCardDTO): SecureCardBO = with(input) {
        SecureCardBO(
            uid = uid,
            cardHolderName = cardHolderName,
            cardNumber = cardNumber,
            cardExpiryDate = cardExpiryDate,
            cardCvv = cardCvv,
            cardProvider = CardProviderEnum.fromName(cardProvider),
            createdAt = createdAt,
            userUid = userUid
        )
    }

    override fun mapInListToOutList(input: Iterable<SecureCardDTO>): Iterable<SecureCardBO> =
        input.map(::mapInToOut)

    override fun mapOutListToInList(input: Iterable<SecureCardBO>): Iterable<SecureCardDTO> =
        input.map(::mapOutToIn)

    override fun mapOutToIn(input: SecureCardBO): SecureCardDTO = with(input) {
        SecureCardDTO(
            uid = uid,
            cardHolderName = cardHolderName,
            cardNumber = cardNumber,
            cardExpiryDate = cardExpiryDate,
            cardCvv = cardCvv,
            cardProvider = cardProvider.name,
            createdAt = createdAt,
            userUid = userUid
        )
    }
}