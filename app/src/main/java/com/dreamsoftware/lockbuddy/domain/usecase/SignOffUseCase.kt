package com.dreamsoftware.lockbuddy.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.lockbuddy.domain.repository.IUserRepository

/**
 * Close session use case
 * @param userRepository
 */
class SignOffUseCase(
    private val userRepository: IUserRepository
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        userRepository.closeSession()
    }
}