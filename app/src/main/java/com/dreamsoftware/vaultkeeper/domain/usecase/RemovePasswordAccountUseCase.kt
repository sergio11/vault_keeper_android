package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository

class RemovePasswordAccountUseCase(
    private val preferenceRepository: IPreferenceRepository,
    private val accountRepository: IAccountRepository
): BrownieUseCaseWithParams<RemovePasswordAccountUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        accountRepository.deleteById(accountUid = params.uid, userUid = preferenceRepository.getAuthUserUid())
    }

    data class Params(
        val uid: String
    )
}