package com.dreamsoftware.lockbuddy.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.lockbuddy.domain.model.AuthUserBO
import com.dreamsoftware.lockbuddy.domain.repository.IUserRepository

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