package com.dreamsoftware.vaultkeeper.data.preferences.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dreamsoftware.vaultkeeper.data.preferences.IPreferencesDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class PreferencesDataSourceImpl(
    private val dataStore: DataStore<Preferences>
): IPreferencesDataSource {
    private companion object {

        @JvmStatic
        val AUTH_USER_UID_KEY = stringPreferencesKey("auth_user_uid_key")

        @JvmStatic
        val BIOMETRIC_AUTH_KEY = stringPreferencesKey("biometric_auth_key")
    }

    override suspend fun saveAuthUserUid(uid: String) {
        dataStore.edit {
            it[AUTH_USER_UID_KEY] = uid
        }
    }

    override suspend fun getAuthUserUid(): String = dataStore.data.map {
        it[AUTH_USER_UID_KEY].orEmpty()
    }.first()

    override suspend fun hasBiometricAuthEnabled(): Boolean = dataStore.data.map {
        it[BIOMETRIC_AUTH_KEY].orEmpty()
    }.first().toBoolean()

    override suspend fun updateBiometricAuthState(isEnabled: Boolean) {
        dataStore.edit {
            it[BIOMETRIC_AUTH_KEY] = isEnabled.toString()
        }
    }

    override suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }
}