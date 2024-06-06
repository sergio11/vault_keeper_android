package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.exception.InvalidDataException
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.model.SignUpBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator

class SignUpUseCase(
    private val preferenceRepository: IPreferenceRepository,
    private val userRepository: IUserRepository,
    private val validator: IBusinessEntityValidator<SignUpBO>
): BrownieUseCaseWithParams<SignUpUseCase.Params, AuthUserBO>() {

    override suspend fun onExecuted(params: Params): AuthUserBO = with(params) {
        params.toSignUpBO().let { signUpBO ->
            validator.validate(signUpBO).takeIf { it.isNotEmpty() }?.let { errors ->
                throw InvalidDataException(errors, "Invalid data provided")
            } ?: run {
                userRepository.signUp(signUpBO).also {
                    preferenceRepository.saveAuthUserUid(it.uid)
                }
            }
        }
    }

    private fun Params.toSignUpBO() =
        SignUpBO(
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )

    data class Params(
        val email: String,
        val password: String,
        val confirmPassword: String
    )
}