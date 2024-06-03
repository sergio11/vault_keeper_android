package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository

/**
 * Close session use case
 * @param userRepository
 */
class SignOffUseCase(
    private val userRepository: IUserRepository,
    private val preferenceRepository: IPreferenceRepository
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        userRepository.closeSession()
        preferenceRepository.clearData()
    }
}