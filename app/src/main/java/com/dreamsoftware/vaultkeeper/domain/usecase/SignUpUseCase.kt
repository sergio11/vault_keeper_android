package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository

/**
 * Sign Use case
 * @param userRepository
 * @param secretRepository
 */
class SignUpUseCase(
    private val userRepository: IUserRepository,
    private val secretRepository: ISecretRepository,
): BrownieUseCaseWithParams<SignUpUseCase.Params, AuthUserBO>() {

    override suspend fun onExecuted(params: Params): AuthUserBO = with(params) {
        userRepository.signUp(email, password).also {
            secretRepository.generate(it.uid)
        }
    }

    data class Params(
        val email: String,
        val password: String
    )
}