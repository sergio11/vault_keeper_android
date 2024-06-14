package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.ICredentialBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class GetAllCredentialsUseCase(
    private val preferenceRepository: IPreferenceRepository,
    private val accountRepository: IAccountRepository,
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCaseWithParams<GetAllCredentialsUseCase.Params, List<ICredentialBO>>() {

    override suspend fun onExecuted(params: Params): List<ICredentialBO> = with(params) {
        val authUserUid = preferenceRepository.getAuthUserUid()
        val accountsByUser = accountRepository.findAllByUserIdWhere(userUid = authUserUid, term = term)
        val secureCardsByUser = secureCardRepository.findAllByUserIdWhere(userUid = authUserUid, term = term)
        accountsByUser + secureCardsByUser
    }

    data class Params(
        val term: String?
    )
}