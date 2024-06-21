package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware

class UnLockAccountUseCase(
    private val applicationAware: IVaultKeeperApplicationAware
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        applicationAware.unlockAccount()
    }
}