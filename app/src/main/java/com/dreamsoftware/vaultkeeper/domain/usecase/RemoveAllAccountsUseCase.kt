package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository

class RemoveAllAccountsUseCase(
    private val accountRepository: IAccountRepository
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        accountRepository.deleteAll()
    }
}