package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository

/**
 * VerifyUserSessionUseCase
 * @param userRepository
 */
class VerifyUserSessionUseCase(
    private val userRepository: IUserRepository,
) : BrownieUseCase<Boolean>() {

    override suspend fun onExecuted(): Boolean = runCatching {
        userRepository.isAuthenticated()
    }.getOrDefault(false)
}