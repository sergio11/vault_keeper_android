package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository

class GetAllAccountsUseCase(
    private val preferenceRepository: IPreferenceRepository,
    private val accountRepository: IAccountRepository
): BrownieUseCaseWithParams<GetAllAccountsUseCase.Params, List<AccountPasswordBO>>() {

    override suspend fun onExecuted(params: Params): List<AccountPasswordBO> =
        accountRepository.findAllByUserIdWhere(userUid = preferenceRepository.getAuthUserUid(), term = params.term)

    data class Params(
        val term: String?
    )
}