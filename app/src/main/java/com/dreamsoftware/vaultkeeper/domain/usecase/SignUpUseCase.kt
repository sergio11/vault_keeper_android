package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository

/**
 * Sign Use case
 * @param userRepository
 */
class SignUpUseCase(
    private val userRepository: IUserRepository
): BrownieUseCaseWithParams<SignUpUseCase.Params, AuthUserBO>() {

    override suspend fun onExecuted(params: Params): AuthUserBO = with(params) {
        userRepository.signUp(email, password)
    }

    data class Params(
        val email: String,
        val password: String
    )
}