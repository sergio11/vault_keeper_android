package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.AuthRequestBO
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository

/**
 * SignIn Use Case
 * @param userRepository
 * @param preferenceRepository
 */
class SignInUseCase(
    private val userRepository: IUserRepository,
    private val preferenceRepository: IPreferenceRepository
) : BrownieUseCaseWithParams<SignInUseCase.Params, AuthUserBO>() {

    override suspend fun onExecuted(params: Params): AuthUserBO = with(params) {
        userRepository.signIn(AuthRequestBO(email, password)).also {
            preferenceRepository.saveAuthUserUid(uid = it.uid)
        }
    }

    data class Params(
        val email: String,
        val password: String
    )
}