package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecretRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.domain.exception.GetSecretException
import com.dreamsoftware.vaultkeeper.domain.exception.SaveSecretException
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.model.SaveMasterKeyBO
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
    private val secretDataSource: ISecretRemoteDataSource,
    private val passwordGenerator: IPasswordGeneratorService,
    private val pbeDataMapper: IBrownieOneSideMapper<SecretDTO, PBEDataBO>,
    private val cryptoService: ICryptoService
) : ISecretRepository {

    private companion object {
        const val SECRET_SALT_LENGTH = 20
    }

    private val rootPBEData by lazy {
        VaultKeeperNativeSecretsAPI().let {
            PBEDataBO(secret = it.getMasterPassword(), salt = it.getMasterSalt())
        }
    }

    @Throws(SaveSecretException::class)
    override suspend fun save(secret: SaveMasterKeyBO): PBEDataBO = try {
        val secretSalt = passwordGenerator.generatePassword(length = SECRET_SALT_LENGTH)
        val secretEncrypted = cryptoService.encryptAndEncode(password = rootPBEData.secret, salt = rootPBEData.salt, data = secret.key)
        val secretSaltEncrypted = cryptoService.encryptAndEncode(password = rootPBEData.secret, salt = rootPBEData.salt, data = secretSalt)
        secretDataSource.save(SecretDTO(secret.userUid, secretEncrypted, secretSaltEncrypted))
        mapAndDecrypt(secretDataSource.getByUserUid(secret.userUid))
    } catch (ex: Exception) {
        ex.printStackTrace()
        throw SaveSecretException("An error occurred when trying to save secret information", ex)
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