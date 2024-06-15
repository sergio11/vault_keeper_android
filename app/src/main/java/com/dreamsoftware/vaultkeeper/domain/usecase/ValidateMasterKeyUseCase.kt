package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.model.ValidateSecretBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository

class ValidateMasterKeyUseCase(
    private val preferencesRepository: IPreferenceRepository,
    private val secretRepository: ISecretRepository
): BrownieUseCaseWithParams<ValidateMasterKeyUseCase.Params, PBEDataBO>() {

    override suspend fun onExecuted(params: Params): PBEDataBO =
        params.toValidateSecretBO(userUid = preferencesRepository.getAuthUserUid())
            .let { secretRepository.validateSecretForUser(it) }

    private fun Params.toValidateSecretBO(userUid: String) = ValidateSecretBO(
        key = key,
        userUid = userUid
    )

    data class Params(
        val key: String
    )
}