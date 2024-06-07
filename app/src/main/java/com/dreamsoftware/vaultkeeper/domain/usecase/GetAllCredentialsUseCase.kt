package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.model.ICredentialBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class GetAllCredentialsUseCase(
    private val preferenceRepository: IPreferenceRepository,
    private val accountRepository: IAccountRepository,
    private val secureCardRepository: ISecureCardRepository
): BrownieUseCase<List<ICredentialBO>>() {
    override suspend fun onExecuted(): List<ICredentialBO> {
        val authUserUid = preferenceRepository.getAuthUserUid()
        val accountsByUser = accountRepository.findAllByUserId(userUid = authUserUid)
        val secureCardsByUser = secureCardRepository.findAllByUserId(userUid = authUserUid)
        return accountsByUser + secureCardsByUser
    }
}