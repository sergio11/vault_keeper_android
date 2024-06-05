package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository

class RemoveAllAccountsUseCase(
    private val accountRepository: IAccountRepository,
    private val preferenceRepository: IPreferenceRepository,
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        accountRepository.deleteAllByUserId(userUid = preferenceRepository.getAuthUserUid())
    }
}