package com.dreamsoftware.vaultkeeper.service.protection

import com.dreamsoftware.vaultkeeper.domain.model.ICryptable
import com.dreamsoftware.vaultkeeper.domain.model.ICryptoVisitor
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.service.ICryptoService
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService

internal class DataProtectionServiceImpl(
    private val cryptoService: ICryptoService,
    private val secretRepository: ISecretRepository,
    private val preferenceRepository: IPreferenceRepository
): IDataProtectionService {

    override suspend fun <T: ICryptable<T>> wrap(data: T): T {
        val userUid = preferenceRepository.getAuthUserUid()
        val userSecret = secretRepository.getSecretForUser(userUid)
        val encryptVisitor = EncryptVisitor(userSecret)
        return data.accept(encryptVisitor)
    }

    override suspend fun <T: ICryptable<T>> unwrap(data: T): T {
        val userUid = preferenceRepository.getAuthUserUid()
        val userSecret = secretRepository.getSecretForUser(userUid)
        val encryptVisitor = DecryptVisitor(userSecret)
        return data.accept(encryptVisitor)
    }

    private inner class EncryptVisitor(private val userSecret: PBEDataBO) : ICryptoVisitor {
        override fun visit(field: String, value: String): String =
            cryptoService.encryptAndEncode(
                password = userSecret.secret,
                salt = userSecret.salt,
                data = value
            )
    }

    private inner class DecryptVisitor(private val userSecret: PBEDataBO) : ICryptoVisitor {
        override fun visit(field: String, value: String): String = cryptoService.decodeAndDecrypt(
            password = userSecret.secret,
            salt = userSecret.salt,
            data = value
        )
    }
}