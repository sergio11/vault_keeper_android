package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class GetAllCardsUseCase(
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCase<List<SecureCardBO>>() {
    override suspend fun onExecuted(): List<SecureCardBO> =
        secureCardRepository.findAll()
}