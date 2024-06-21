package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.PreferenceDataException

interface IPreferenceRepository {

    @Throws(PreferenceDataException::class)
    suspend fun saveAuthUserUid(uid: String)

    @Throws(PreferenceDataException::class)
    suspend fun getAuthUserUid(): String

    @Throws(PreferenceDataException::class)
    suspend fun hasBiometricAuthEnabled(): Boolean

    @Throws(PreferenceDataException::class)
    suspend fun updateBiometricAuthState(isEnabled: Boolean)

    @Throws(PreferenceDataException::class)
    suspend fun clearData()
}