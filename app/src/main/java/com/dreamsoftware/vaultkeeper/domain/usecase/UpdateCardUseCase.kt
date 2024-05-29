package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class UpdateCardUseCase(
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCaseWithParams<UpdateCardUseCase.Params, SecureCardBO>() {

    override suspend fun onExecuted(params: Params): SecureCardBO = with(params) {
        secureCardRepository.getCardById(id).let {
            it.copy(
                cardHolderName = cardHolderName ?: it.cardHolderName,
                cardNumber = cardNumber ?: it.cardNumber,
                cardExpiryDate = cardExpiryDate ?: it.cardExpiryDate,
                cardCvv = cardCvv ?: it.cardCvv,
                cardProvider = cardProvider ?: it.cardProvider
            )
        }.also {
            secureCardRepository.updateCard(it)
        }
    }

    data class Params(
        val id: Int,
        val cardHolderName: String?,
        val cardNumber: String?,
        val cardExpiryDate: String?,
        val cardCvv: String?,
        val cardProvider: String?
    )
}