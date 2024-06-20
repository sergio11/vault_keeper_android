package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.remote.datasource.ISecretRemoteDataSource
import com.dreamsoftware.vaultkeeper.data.remote.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.data.remote.exception.SaveSecretException
import com.dreamsoftware.vaultkeeper.data.remote.exception.SecretNotFoundException
import com.dreamsoftware.vaultkeeper.data.remote.exception.VerifySecretsException
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.exception.NotSecretFoundException
import com.dreamsoftware.vaultkeeper.domain.exception.SaveSecretDetailsException
import com.dreamsoftware.vaultkeeper.domain.exception.ValidateSecretException
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.model.SaveSecretBO
import com.dreamsoftware.vaultkeeper.domain.model.ValidateSecretBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.service.IDataProtectionService
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Secret Repository Impl
 * @param secretDataSource
 * @param passwordGenerator
 * @param dataProtectionService
 * @param secretMapper
 * @param saveSecretMapper
 * @param dispatcher
 */
internal class SecretRepositoryImpl(
    private val secretDataSource: ISecretRemoteDataSource,
    private val passwordGenerator: IPasswordGeneratorService,
    private val dataProtectionService: IDataProtectionService,
    private val secretMapper: IBrownieOneSideMapper<SecretDTO, PBEDataBO>,
    private val saveSecretMapper: IBrownieOneSideMapper<SaveSecretBO, SecretDTO>,
    dispatcher: CoroutineDispatcher
) : SupportRepositoryImpl(dispatcher), ISecretRepository {

    @Throws(SaveSecretDetailsException::class)
    override suspend fun save(secret: SaveSecretBO): PBEDataBO = safeExecute {
        try {
            dataProtectionService.wrapAsRoot(secret)
                .let(saveSecretMapper::mapInToOut)
                .let { secretDataSource.save(it) }
                .let { secretDataSource.getByUserUid(secret.userUid) }
                .let(secretMapper::mapInToOut)
                .let { dataProtectionService.unwrapAsRoot(it) }
        } catch (ex: SaveSecretException) {
            ex.printStackTrace()
            throw SaveSecretDetailsException("An error occurred when trying to save secret information", ex)
        }
    }

    @Throws(SecretNotFoundException::class)
    override suspend fun getSecretForUser(userUid: String): PBEDataBO = safeExecute {
        try {
            secretDataSource.getByUserUid(userUid)
                .let(secretMapper::mapInToOut)
                .let { dataProtectionService.unwrapAsRoot(it) }
        } catch (ex: SecretNotFoundException) {
            throw NotSecretFoundException("An error occurred when trying to get secret information", ex)
        }
    }

    @Throws(ValidateSecretException::class)
    override suspend fun validateSecretForUser(secret: ValidateSecretBO): PBEDataBO = safeExecute {
        try {
            secretDataSource.getByUserUid(secret.userUid)
                .let(secretMapper::mapInToOut)
                .let { dataProtectionService.unwrapAsRoot(it) }
                .takeIf { it.secret == secret.key } ?: throw SecretNotFoundException("Secrets don't match")
        } catch (ex: SecretNotFoundException) {
            throw ValidateSecretException("Validation secret failed", ex)
        }
    }

    @Throws(SecretNotFoundException::class)
    override suspend fun hasSecret(userUid: String): Boolean = safeExecute {
        try {
            secretDataSource.hasSecretByUserUid(userUid)
        } catch (ex: VerifySecretsException) {
            throw NotSecretFoundException("An error occurred when trying to verify secrets", ex)
        }
    }
}