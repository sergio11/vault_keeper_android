package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.vaultkeeper.data.preferences.IPreferencesDataSource
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.exception.PreferenceDataException
import com.dreamsoftware.vaultkeeper.domain.repository.IPreferenceRepository
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Preference Repository
 * @param preferenceDataSource
 */
internal class PreferenceRepositoryImpl(
    private val preferenceDataSource: IPreferencesDataSource,
    dispatcher: CoroutineDispatcher
): SupportRepositoryImpl(dispatcher), IPreferenceRepository {

    @Throws(PreferenceDataException::class)
    override suspend fun saveAuthUserUid(uid: String) = safeExecute {
        try {
            preferenceDataSource.saveAuthUserUid(uid)
        } catch (ex: Exception) {
            throw PreferenceDataException("An error occurred when trying to save user uid", ex)
        }
    }

    @Throws(PreferenceDataException::class)
    override suspend fun getAuthUserUid(): String = safeExecute {
        try {
            preferenceDataSource.getAuthUserUid()
        } catch (ex: Exception) {
            throw PreferenceDataException("An error occurred when trying to get user uid", ex)
        }
    }

    @Throws(PreferenceDataException::class)
    override suspend fun hasBiometricAuthEnabled(): Boolean = safeExecute {
        try {
            preferenceDataSource.hasBiometricAuthEnabled()
        } catch (ex: Exception) {
            throw PreferenceDataException("An error occurred when trying check biometric auth state", ex)
        }
    }

    @Throws(PreferenceDataException::class)
    override suspend fun updateBiometricAuthState(isEnabled: Boolean) = safeExecute {
        try {
            preferenceDataSource.updateBiometricAuthState(isEnabled)
        } catch (ex: Exception) {
            throw PreferenceDataException("An error occurred when trying to update biometric auth status", ex)
        }
    }

    @Throws(PreferenceDataException::class)
    override suspend fun clearData() = safeExecute {
        try {
            preferenceDataSource.clearData()
        } catch (ex: Exception) {
            throw PreferenceDataException("An error occurred when trying to clear data", ex)
        }
    }
}