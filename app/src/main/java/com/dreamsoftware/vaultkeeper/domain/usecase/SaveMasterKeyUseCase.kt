package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.exception.InvalidDataException
import com.dreamsoftware.vaultkeeper.domain.model.SaveMasterKeyBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator

class SaveMasterKeyUseCase(
    private val preferencesRepository: IPreferenceRepository,
    private val secretRepository: ISecretRepository,
    private val masterKeyValidator: IBusinessEntityValidator<SaveMasterKeyBO>
): BrownieUseCaseWithParams<SaveMasterKeyUseCase.Params, Boolean>() {

    override suspend fun onExecuted(params: Params): Boolean =
        params.toSaveSecretBO(userUid = preferencesRepository.getAuthUserUid()).let { saveSecretBO ->
            masterKeyValidator.validate(saveSecretBO).takeIf { it.isNotEmpty() }?.let { errors ->
                throw InvalidDataException(errors, "Invalid data provided")
            } ?: run {
                secretRepository.save(saveSecretBO)
                true
            }
        }

    private fun Params.toSaveSecretBO(userUid: String) = SaveMasterKeyBO(
        key = key,
        confirmKey = confirmKey,
        userUid = userUid
    )

    data class Params(
        val key: String,
        val confirmKey: String
    )
}