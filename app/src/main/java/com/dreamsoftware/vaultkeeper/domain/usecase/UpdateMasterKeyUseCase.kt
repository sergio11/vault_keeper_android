package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.exception.InvalidDataException
import com.dreamsoftware.vaultkeeper.domain.model.SaveSecretBO
import com.dreamsoftware.vaultkeeper.domain.model.ValidateSecretBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator

class UpdateMasterKeyUseCase(
    private val preferencesRepository: IPreferenceRepository,
    private val secretRepository: ISecretRepository,
    private val masterKeyValidator: IBusinessEntityValidator<SaveSecretBO>,
    private val passwordGeneratorService: IPasswordGeneratorService,
    private val secureCardRepository: ISecureCardRepository,
    private val accountRepository: IAccountRepository
): BrownieUseCaseWithParams<UpdateMasterKeyUseCase.Params, Unit>() {

    private companion object {
        const val SECRET_SALT_LENGTH = 20
    }

    override suspend fun onExecuted(params: Params): Unit = with(params) {
        with(secretRepository) {
            val userUid = preferencesRepository.getAuthUserUid()
            validateSecretForUser(ValidateSecretBO(key = key, userUid = userUid))
            val secureCards = secureCardRepository.findAllByUserId(userUid)
            val accounts = accountRepository.findAllByUserId(userUid)
            toSaveSecretBO(userUid = userUid).let { saveSecretBO ->
                masterKeyValidator.validate(saveSecretBO).takeIf { it.isNotEmpty() }?.let { errors ->
                    throw InvalidDataException(errors, "Invalid data provided")
                } ?: run {
                    deleteUserSecret(userUid)
                    save(saveSecretBO).also {
                        secureCardRepository.update(secureCards)
                        accountRepository.update(accounts)
                    }
                }
            }
        }
    }

    private fun Params.toSaveSecretBO(userUid: String) = SaveSecretBO(
        key = newKey,
        confirmKey = confirmKey,
        userUid = userUid,
        salt = passwordGeneratorService.generatePassword(length = SECRET_SALT_LENGTH)
    )

    data class Params(
        val key: String,
        val newKey: String,
        val confirmKey: String
    )
}