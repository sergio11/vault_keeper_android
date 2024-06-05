package com.dreamsoftware.vaultkeeper.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.vaultkeeper.domain.repository.IAccountRepository
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecureCardRepository

class RemoveAllCredentialsUseCase(
    private val accountRepository: IAccountRepository,
    private val secureCardRepository: ISecureCardRepository,
    private val preferenceRepository: IPreferenceRepository,
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        val userUid = preferenceRepository.getAuthUserUid()
        accountRepository.deleteAllByUserId(userUid = userUid)
        secureCardRepository.deleteAllByUserId(userUid = userUid)
    }
}