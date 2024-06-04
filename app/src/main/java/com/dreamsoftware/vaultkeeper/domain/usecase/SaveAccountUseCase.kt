package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.domain.exception.InvalidDataException
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.validation.IBusinessEntityValidator

class SaveAccountUseCase(
    private val accountRepository: IAccountRepository,
    private val accountValidator: IBusinessEntityValidator<AccountBO>
): BrownieUseCaseWithParams<SaveAccountUseCase.Params, AccountBO>() {

    override suspend fun onExecuted(params: Params): AccountBO =
        params.toAccountBO().let { secureCardBO ->
            accountValidator.validate(secureCardBO).takeIf { it.isNotEmpty() }?.let { errors ->
                throw InvalidDataException(errors, "Invalid data provided")
            } ?: run {
                accountRepository.insert(secureCardBO)
            }
        }

    private fun Params.toAccountBO() = AccountBO(
        uid = uid ?: String.EMPTY,
        accountName = accountName,
        userName = userName,
        email = email,
        mobileNumber = mobileNumber,
        password = password,
        note = note,
        createdAt = System.currentTimeMillis()
    )

    data class Params(
        val uid: String? = null,
        val accountName: String,
        val userName: String,
        val email: String,
        val mobileNumber: String,
        val password: String,
        val note: String
    )
}