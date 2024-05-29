package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class RemoveAllCardsUseCase(
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        secureCardRepository.removeAllCards()
    }
}