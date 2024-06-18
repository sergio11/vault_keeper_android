package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware

class SignOffUseCase(
    private val userRepository: IUserRepository,
    private val preferenceRepository: IPreferenceRepository,
    private val applicationAware: IVaultKeeperApplicationAware
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        userRepository.closeSession()
        preferenceRepository.clearData()
        applicationAware.lockAccount()
    }
}