package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class RemoveCardUseCase(
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCaseWithParams<RemoveCardUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        secureCardRepository.deleteById(params.uid)
    }

    data class Params(
        val uid: String
    )
}