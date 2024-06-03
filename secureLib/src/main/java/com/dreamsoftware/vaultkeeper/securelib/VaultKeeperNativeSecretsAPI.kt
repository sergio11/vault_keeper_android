package com.dreamsoftware.vaultkeeper.securelib

class VaultKeeperNativeSecretsAPI {

    external fun getMasterPassword(): String

    external fun getMasterSalt(): String

    companion object {
        // Used to load the 'securelib' library on application startup.
        init {
            System.loadLibrary("securelib")
        }
    }
}