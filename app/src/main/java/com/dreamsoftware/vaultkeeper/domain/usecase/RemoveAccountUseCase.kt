package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository

class RemoveAccountUseCase(
    private val accountRepository: IAccountRepository
): BrownieUseCaseWithParams<RemoveAccountUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        accountRepository.deleteById(params.uid)
    }

    data class Params(
        val uid: String
    )
}