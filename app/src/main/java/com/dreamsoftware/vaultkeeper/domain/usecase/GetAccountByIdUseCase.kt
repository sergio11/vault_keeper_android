package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository

class GetAccountByIdUseCase(
    private val accountRepository: IAccountRepository,
    private val preferenceRepository: IPreferenceRepository
): BrownieUseCaseWithParams<GetAccountByIdUseCase.Params, AccountBO>() {

    override suspend fun onExecuted(params: Params): AccountBO =
        accountRepository.findById(accountUid = params.uid, userUid = preferenceRepository.getAuthUserUid())

    data class Params(
        val uid: String
    )
}