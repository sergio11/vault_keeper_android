package com.dreamsoftware.vaultkeeper.ui.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.biometric.BiometricManager
import com.dreamsoftware.vaultkeeper.R

fun ConnectivityManager.isNetworkConnected(): Boolean {
    val capabilities = getNetworkCapabilities(activeNetwork)
    return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}


fun Context.shareApp() {
    startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
    }, "Share app link"))
}

fun Context.isBiometricSupported(): Boolean {
    val biometricManager = BiometricManager.from(this)
    val canAuthenticate =
        biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
    when (canAuthenticate) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            // The user can authenticate with biometrics, continue with the authentication process
            return true
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE, BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE, BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // Handle the error cases as needed in your app
            return false
        }

        else -> {
            // Biometric status unknown or another error occurred
            return false
        }
    }
}