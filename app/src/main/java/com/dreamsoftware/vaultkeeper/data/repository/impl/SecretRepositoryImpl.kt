package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.firebase.datasource.ISecretDataSource
import com.dreamsoftware.vaultkeeper.data.firebase.dto.SecretDTO
import com.dreamsoftware.vaultkeeper.domain.exception.GenerateSecretException
import com.dreamsoftware.vaultkeeper.domain.exception.GetSecretException
import com.dreamsoftware.vaultkeeper.domain.model.PBEDataBO
import com.dreamsoftware.vaultkeeper.domain.repository.ISecretRepository
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService

/**
 * Secret Repository Impl
 * @param secretDataSource
 * @param passwordGenerator
 * @param pbeDataMapper
 */
internal class SecretRepositoryImpl(
    private val secretDataSource: ISecretDataSource,
    private val passwordGenerator: IPasswordGeneratorService,
    private val pbeDataMapper: IBrownieOneSideMapper<SecretDTO, PBEDataBO>
) : ISecretRepository {

    private companion object {
        const val SECRET_LENGTH = 60
        const val SECRET_SALT_LENGTH = 20
    }

    @Throws(GenerateSecretException::class)
    override suspend fun generate(userUid: String): PBEDataBO = try {
        val secret = passwordGenerator.generatePassword(length = SECRET_LENGTH)
        val secretSalt = passwordGenerator.generatePassword(length = SECRET_SALT_LENGTH)
        secretDataSource.save(SecretDTO(userUid, secret, secretSalt))
        pbeDataMapper.mapInToOut(secretDataSource.getByUserUid(userUid))
    } catch (ex: Exception) {
        ex.printStackTrace()
        throw GenerateSecretException("An error occurred when trying to save secret information", ex)
    }

    @Throws(GetSecretException::class)
    override suspend fun get(userUid: String): PBEDataBO = try {
        val secret = secretDataSource.getByUserUid(userUid)
        pbeDataMapper.mapInToOut(secret)
    } catch (ex: Exception) {
        throw GetSecretException("An error occurred when trying to get secret information", ex)
    }
}