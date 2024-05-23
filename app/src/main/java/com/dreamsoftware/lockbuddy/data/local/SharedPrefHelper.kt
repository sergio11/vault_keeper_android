package com.dreamsoftware.lockbuddy.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPrefHelper @Inject constructor(
    private val pref: SharedPreferences
) {
    companion object {
        private const val KEY_AUTH = "auth_key"
        private const val KEY_SWITCH_STATE = "switchState"
    }

    var masterKey: String
        get() = getString(KEY_AUTH)
        set(value) {
            setString(value)
        }

    private fun getString(key: String): String {
        return pref.getString(key, "") ?: ""
    }

    private fun setString(value: String) {
        pref.edit {
            putString(KEY_AUTH, value)
        }
    }

    fun resetMasterKeyAndSwitch() {
        pref.edit {
            remove(KEY_AUTH)
            remove(KEY_SWITCH_STATE)
        }
    }

    fun getSwitchState(): Boolean {
        return pref.getBoolean(KEY_SWITCH_STATE, false)
    }

    fun setSwitchState(state: Boolean) {
        pref.edit { putBoolean(KEY_SWITCH_STATE, state) }
    }
}