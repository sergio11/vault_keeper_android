package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.exception.InvalidDataException
import com.dreamsoftware.vaultkeeper.domain.model.SaveSecretBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware

class CreateMasterKeyUseCase(
    private val preferencesRepository: IPreferenceRepository,
    private val secretRepository: ISecretRepository,
    private val masterKeyValidator: IBusinessEntityValidator<SaveSecretBO>,
    private val passwordGeneratorService: IPasswordGeneratorService,
    private val applicationAware: IVaultKeeperApplicationAware
): BrownieUseCaseWithParams<CreateMasterKeyUseCase.Params, Unit>() {

    private companion object {
        const val SECRET_SALT_LENGTH = 20
    }

    override suspend fun onExecuted(params: Params): Unit =
        params.toSaveSecretBO(userUid = preferencesRepository.getAuthUserUid()).let { saveSecretBO ->
            masterKeyValidator.validate(saveSecretBO).takeIf { it.isNotEmpty() }?.let { errors ->
                throw InvalidDataException(errors, "Invalid data provided")
            } ?: run {
                secretRepository.save(saveSecretBO).also {
                    applicationAware.unlockAccount()
                }
            }
        }

    private fun Params.toSaveSecretBO(userUid: String) = SaveSecretBO(
        key = key,
        confirmKey = confirmKey,
        userUid = userUid,
        salt = passwordGeneratorService.generatePassword(length = SECRET_SALT_LENGTH)
    )

    data class Params(
        val key: String,
        val confirmKey: String
    )
}