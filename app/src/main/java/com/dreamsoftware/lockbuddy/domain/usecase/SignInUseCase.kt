package com.dreamsoftware.lockbuddy.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.lockbuddy.domain.model.AuthRequestBO
import com.dreamsoftware.lockbuddy.domain.model.AuthUserBO
import com.dreamsoftware.lockbuddy.domain.repository.IUserRepository

/**
 * SignIn Use Case
 * @param userRepository
 */
class SignInUseCase(
    private val userRepository: IUserRepository
) : BrownieUseCaseWithParams<SignInUseCase.Params, AuthUserBO>() {

    override suspend fun onExecuted(params: Params): AuthUserBO = with(params) {
        userRepository.signIn(AuthRequestBO(email, password))
    }

    data class Params(
        val email: String,
        val password: String
    )
}