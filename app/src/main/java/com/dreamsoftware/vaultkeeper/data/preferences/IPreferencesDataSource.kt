package com.dreamsoftware.vaultkeeper.data.preferences

interface IPreferencesDataSource {
    suspend fun saveAuthUserUid(uid: String)

    suspend fun getAuthUserUid(): String

    suspend fun hasBiometricAuthEnabled(): Boolean

    suspend fun updateBiometricAuthState(isEnabled: Boolean)

    suspend fun clearData()
}