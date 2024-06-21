package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository

class UpdateBiometricAuthStateUseCase(
    private val preferenceRepository: IPreferenceRepository
): BrownieUseCaseWithParams<UpdateBiometricAuthStateUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        preferenceRepository.updateBiometricAuthState(isEnabled = params.isEnabled)
    }

    data class Params(
        val isEnabled: Boolean
    )
}