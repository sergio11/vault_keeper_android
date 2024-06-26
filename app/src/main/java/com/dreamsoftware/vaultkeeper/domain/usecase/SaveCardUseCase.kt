package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.exception.InvalidDataException
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.vaultkeeper.utils.generateUUID

class SaveCardUseCase(
    private val preferencesRepository: IPreferenceRepository,
    private val secureCardRepository: ISecureCardRepository,
    private val secureCardValidator: IBusinessEntityValidator<SecureCardBO>
): BrownieUseCaseWithParams<SaveCardUseCase.Params, SecureCardBO>() {

    override suspend fun onExecuted(params: Params): SecureCardBO =
        params.toSecureCardBO(userUid = preferencesRepository.getAuthUserUid()).let { secureCardBO ->
            secureCardValidator.validate(secureCardBO).takeIf { it.isNotEmpty() }?.let { errors ->
                throw InvalidDataException(errors, "Invalid data provided")
            } ?: run {
                secureCardRepository.insert(secureCardBO)
            }
        }

    private fun Params.toSecureCardBO(userUid: String) = SecureCardBO(
        uid = uid ?: generateUUID(),
        cardHolderName = cardHolderName,
        cardNumber = cardNumber,
        cardExpiryDate = cardExpiryDate,
        cardCvv = cardCvv,
        cardProvider = cardProvider,
        createdAt = System.currentTimeMillis(),
        userUid = userUid
    )

    data class Params(
        val uid: String? = null,
        val cardHolderName: String,
        val cardNumber: String,
        val cardExpiryDate: String,
        val cardCvv: String,
        val cardProvider: CardProviderEnum
    )
}