package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.firebase.datasource.ISecretDataSource
import com.dreamsoftware.vaultkeeper.data.firebase.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.domain.exception.GenerateSecretException
import com.dreamsoftware.vaultkeeper.domain.exception.GetSecretException
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.service.ICryptoService
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
import com.dreamsoftware.vaultkeeper.securelib.VaultKeeperNativeSecretsAPI

/**
 * Secret Repository Impl
 * @param secretDataSource
 * @param passwordGenerator
 * @param pbeDataMapper
 * @param cryptoService
 */
internal class SecretRepositoryImpl(
    private val secretDataSource: ISecretDataSource,
    private val passwordGenerator: IPasswordGeneratorService,
    private val pbeDataMapper: IBrownieOneSideMapper<SecretDTO, PBEDataBO>,
    private val cryptoService: ICryptoService
) : ISecretRepository {

    private companion object {
        const val SECRET_LENGTH = 60
        const val SECRET_SALT_LENGTH = 20
    }

    private val rootPBEData by lazy {
        VaultKeeperNativeSecretsAPI().let {
            PBEDataBO(secret = it.getMasterPassword(), salt = it.getMasterSalt())
        }
    }

    @Throws(GenerateSecretException::class)
    override suspend fun generate(userUid: String): PBEDataBO = try {
        val secret = passwordGenerator.generatePassword(length = SECRET_LENGTH)
        val secretSalt = passwordGenerator.generatePassword(length = SECRET_SALT_LENGTH)
        val secretEncrypted = cryptoService.encryptAndEncode(password = rootPBEData.secret, salt = rootPBEData.salt, data = secret)
        val secretSaltEncrypted = cryptoService.encryptAndEncode(password = rootPBEData.secret, salt = rootPBEData.salt, data = secretSalt)
        secretDataSource.save(SecretDTO(userUid, secretEncrypted, secretSaltEncrypted))
        mapAndDecrypt(secretDataSource.getByUserUid(userUid))
    } catch (ex: Exception) {
        ex.printStackTrace()
        throw GenerateSecretException("An error occurred when trying to save secret information", ex)
    }

    @Throws(GetSecretException::class)
    override suspend fun getSecretForUser(userUid: String): PBEDataBO = try {
        val secret = secretDataSource.getByUserUid(userUid)
        mapAndDecrypt(secret)
    } catch (ex: Exception) {
        throw GetSecretException("An error occurred when trying to get secret information", ex)
    }

    private fun mapAndDecrypt(secret: SecretDTO): PBEDataBO = pbeDataMapper.mapInToOut(secret).let {
        it.copy(
            secret = cryptoService.decodeAndDecrypt(password = rootPBEData.secret, salt = rootPBEData.salt, data = it.secret),
            salt = cryptoService.decodeAndDecrypt(password = rootPBEData.secret, salt = rootPBEData.salt, data = it.salt)
        )
    }
}