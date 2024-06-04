package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository

class GetAccountByIdUseCase(
    private val accountRepository: IAccountRepository
): BrownieUseCaseWithParams<GetAccountByIdUseCase.Params, AccountBO>() {

    override suspend fun onExecuted(params: Params): AccountBO =
        accountRepository.findById(params.uid)

    data class Params(
        val uid: String
    )
}