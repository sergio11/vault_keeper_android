package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository

class GetAllAccountsUseCase(
    private val accountRepository: IAccountRepository
): BrownieUseCase<List<AccountBO>>() {
    override suspend fun onExecuted(): List<AccountBO> =
        accountRepository.findAll()
}