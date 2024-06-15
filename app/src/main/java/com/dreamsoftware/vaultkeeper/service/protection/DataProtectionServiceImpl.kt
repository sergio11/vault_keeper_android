package com.dreamsoftware.vaultkeeper.service.protection

import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecretRemoteDataSource
import com.dreamsoftware.vaultkeeper.domain.model.ICryptable
import com.dreamsoftware.vaultkeeper.domain.model.ICryptoVisitor
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import com.dreamsoftware.vaultkeeper.domain.service.ICryptoService
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService
import com.dreamsoftware.vaultkeeper.securelib.VaultKeeperNativeSecretsAPI

internal class DataProtectionServiceImpl(
    private val cryptoService: ICryptoService,
    private val secretDataSource: ISecretRemoteDataSource,
    private val preferenceRepository: IPreferenceRepository
): IDataProtectionService {

    private val vaultRootSecret by lazy {
        VaultKeeperNativeSecretsAPI()
    }

    override suspend fun <T : ICryptable<T>> wrapAsRoot(data: T): T =
        wrap(secret = vaultRootSecret.getMasterPassword(), salt = vaultRootSecret.getMasterSalt(), data = data)

    override suspend fun <T: ICryptable<T>> wrap(data: T): T = getAuthUserSecret().let {
        wrap(secret = it.secret, salt = it.salt, data = data)
    }

    override suspend fun <T : ICryptable<T>> unwrapAsRoot(data: T): T =
        unwrap(secret = vaultRootSecret.getMasterPassword(), salt = vaultRootSecret.getMasterSalt(), data = data)

    override suspend fun <T: ICryptable<T>> unwrap(data: T): T = getAuthUserSecret().let {
        unwrap(secret = it.secret, salt = it.salt, data = data)
    }

    private suspend fun getAuthUserSecret() =
        secretDataSource.getByUserUid(preferenceRepository.getAuthUserUid())

    private fun <T: ICryptable<T>> wrap(secret: String, salt: String, data: T): T {
        val encryptVisitor = EncryptVisitor(secret, salt)
        return data.accept(encryptVisitor)
    }

    private fun <T: ICryptable<T>> unwrap(secret: String, salt: String, data: T): T {
        val decryptVisitor = DecryptVisitor(secret, salt)
        return data.accept(decryptVisitor)
    }

    private inner class EncryptVisitor(val secret: String, val salt: String) : ICryptoVisitor {
        override fun visit(field: String, value: String): String =
            cryptoService.encryptAndEncode(
                password = secret,
                salt = salt,
                data = value
            )
    }

    private inner class DecryptVisitor(val secret: String, val salt: String) : ICryptoVisitor {
        override fun visit(field: String, value: String): String = cryptoService.decodeAndDecrypt(
            password = secret,
            salt = salt,
            data = value
        )
    }
}