package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO

internal class SecureCardLocalMapper: IBrownieMapper<SecureCardEntity, SecureCardBO> {
    override fun mapInToOut(input: SecureCardEntity): SecureCardBO = with(input) {
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

    override fun mapInListToOutList(input: Iterable<SecureCardEntity>): Iterable<SecureCardBO> =
        input.map(::mapInToOut)

    override fun mapOutListToInList(input: Iterable<SecureCardBO>): Iterable<SecureCardEntity> =
        input.map(::mapOutToIn)

    override fun mapOutToIn(input: SecureCardBO): SecureCardEntity = with(input) {
        SecureCardEntity(
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