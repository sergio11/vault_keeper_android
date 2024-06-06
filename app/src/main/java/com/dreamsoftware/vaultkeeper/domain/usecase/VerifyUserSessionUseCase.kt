package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository

/**
 * VerifyUserSessionUseCase
 * @param userRepository
 */
class VerifyUserSessionUseCase(
    private val userRepository: IUserRepository,
) : BrownieUseCase<AuthUserBO>() {

    override suspend fun onExecuted(): AuthUserBO = userRepository.getCurrentAuthenticatedUser()
}