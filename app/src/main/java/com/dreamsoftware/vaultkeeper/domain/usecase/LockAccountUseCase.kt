package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware

class LockAccountUseCase(
    private val applicationAware: IVaultKeeperApplicationAware
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        applicationAware.lockAccount()
    }
}