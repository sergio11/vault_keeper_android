package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class RemoveCardUseCase(
    private val preferenceRepository: IPreferenceRepository,
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCaseWithParams<RemoveCardUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        secureCardRepository.deleteById(cardUid = params.uid, userUid = preferenceRepository.getAuthUserUid())
    }

    data class Params(
        val uid: String
    )
}