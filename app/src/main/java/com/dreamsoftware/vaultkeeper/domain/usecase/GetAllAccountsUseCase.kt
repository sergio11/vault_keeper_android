package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository

class GetAllAccountsUseCase(
    private val preferenceRepository: IPreferenceRepository,
    private val accountRepository: IAccountRepository
): BrownieUseCase<List<AccountPasswordBO>>() {
    override suspend fun onExecuted(): List<AccountPasswordBO> =
        accountRepository.findAllByUserId(userUid = preferenceRepository.getAuthUserUid())
}