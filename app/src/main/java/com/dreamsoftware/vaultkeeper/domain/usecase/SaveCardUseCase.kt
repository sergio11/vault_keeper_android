package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.vaultkeeper.domain.exception.InvalidDataException
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator

class SaveCardUseCase(
    private val secureCardRepository: ISecureCardRepository,
    private val secureCardValidator: IBusinessEntityValidator<SecureCardBO>
): BrownieUseCaseWithParams<SaveCardUseCase.Params, SecureCardBO>() {

    override suspend fun onExecuted(params: Params): SecureCardBO =
        params.toSecureCardBO().let { secureCardBO ->
            secureCardValidator.validate(secureCardBO).takeIf { it.isNotEmpty() }?.let { errors ->
                throw InvalidDataException(errors, "Invalid data provided")
            } ?: run {
                secureCardRepository.insert(secureCardBO)
            }
        }

    private fun Params.toSecureCardBO() = SecureCardBO(
        id = id ?: 0,
        cardHolderName = cardHolderName,
        cardNumber = cardNumber,
        cardExpiryDate = cardExpiryDate,
        cardCvv = cardCvv,
        cardProvider = cardProvider,
        createdAt = System.currentTimeMillis()
    )

    data class Params(
        val id: Int? = null,
        val cardHolderName: String,
        val cardNumber: String,
        val cardExpiryDate: String,
        val cardCvv: String,
        val cardProvider: String
    )
}