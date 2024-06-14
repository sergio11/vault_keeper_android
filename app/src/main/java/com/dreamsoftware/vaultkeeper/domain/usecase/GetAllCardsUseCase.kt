package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class GetAllCardsUseCase(
    private val preferencesRepository: IPreferenceRepository,
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCaseWithParams<GetAllCardsUseCase.Params, List<SecureCardBO>>() {
    override suspend fun onExecuted(params: Params): List<SecureCardBO> =
        secureCardRepository.findAllByUserIdWhere(userUid = preferencesRepository.getAuthUserUid(), term = params.term)

    data class Params(
        val term: String?
    )
}