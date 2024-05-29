package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class AddCardUseCase(
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCaseWithParams<AddCardUseCase.Params, SecureCardBO>() {

    override suspend fun onExecuted(params: Params): SecureCardBO = with(params) {
        secureCardRepository.addCard(SecureCardBO(
                id = 0,
                cardHolderName = cardHolderName,
                cardNumber = cardNumber,
                cardExpiryDate = cardExpiryDate,
                cardCvv = cardCvv,
                cardProvider = cardProvider,
                createdAt = createdAt
            ))
    }

    data class Params(
        val cardHolderName: String,
        val cardNumber: String,
        val cardExpiryDate: String,
        val cardCvv: String,
        val cardProvider: String,
        val createdAt: Long
    )
}