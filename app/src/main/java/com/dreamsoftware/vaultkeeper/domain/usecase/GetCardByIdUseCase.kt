package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class GetCardByIdUseCase(
    private val preferencesRepository: IPreferenceRepository,
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCaseWithParams<GetCardByIdUseCase.Params, SecureCardBO>() {

    override suspend fun onExecuted(params: Params): SecureCardBO =
        secureCardRepository.findById(userUid = preferencesRepository.getAuthUserUid(), cardUid = params.uid)

    data class Params(
        val uid: String
    )
}