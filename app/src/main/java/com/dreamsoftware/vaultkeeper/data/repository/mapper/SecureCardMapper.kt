package com.dreamsoftware.vaultkeeper.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieMapper
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO

internal class SecureCardMapper: IBrownieMapper<CardEntity, SecureCardBO> {
    override fun mapInToOut(input: CardEntity): SecureCardBO = with(input) {
        SecureCardBO(
            id = id,
            cardHolderName = cardHolderName,
            cardNumber = cardNumber,
            cardExpiryDate = cardExpiryDate,
            cardCvv = cardCvv,
            cardProvider = cardProvider,
            createdAt = createdAt
        )
    }

    override fun mapInListToOutList(input: Iterable<CardEntity>): Iterable<SecureCardBO> =
        input.map(::mapInToOut)

    override fun mapOutListToInList(input: Iterable<SecureCardBO>): Iterable<CardEntity> =
        input.map(::mapOutToIn)

    override fun mapOutToIn(input: SecureCardBO): CardEntity = with(input) {
        CardEntity(
            id = id,
            cardHolderName = cardHolderName,
            cardNumber = cardNumber,
            cardExpiryDate = cardExpiryDate,
            cardCvv = cardCvv,
            cardProvider = cardProvider,
            createdAt = createdAt
        )
    }
}