package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware

class VerifyUserAccountStatusUseCase(
    private val applicationAware: IVaultKeeperApplicationAware,
) : BrownieUseCase<Boolean>() {

    override suspend fun onExecuted(): Boolean = applicationAware.isAccountUnlocked()
}