package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository

class VerifyBiometricAuthEnabledUseCase(
    private val preferenceRepository: IPreferenceRepository,
) : BrownieUseCase<Boolean>() {

    override suspend fun onExecuted(): Boolean = preferenceRepository.hasBiometricAuthEnabled()
}