package com.dreamsoftware.vaultkeeper.ui.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.biometric.BiometricManager
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum

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


fun String.formatCardNumber(): String {
    val trimmed = if (length >= 16) substring(0..15) else this
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += " "
    }
    return out
}

fun String.formatExpiryDate(): String {
    val trimmed = if (length >= 4) substring(0..3) else this
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i == 1) out += "/" // Add a slash after the first two characters
    }
    return out
}

fun String.obfuscateSecret(takeLast: Int) = takeLast(takeLast).padStart(length, '*')

fun CardProviderEnum?.toCardProviderImage() = this?.let {
    when(it) {
        CardProviderEnum.VISA -> R.drawable.icon_visa
        CardProviderEnum.MASTERCARD -> R.drawable.icon_mastercard
        CardProviderEnum.AMERICAN_EXPRESS -> R.drawable.icon_american_express
        CardProviderEnum.RUPAY -> R.drawable.icon_rupay
        CardProviderEnum.DINERS_CLUB -> R.drawable.icon_diners_club
        CardProviderEnum.OTHER -> R.drawable.icon_card
    }
} ?: R.drawable.icon_card

fun String?.toCardProviderImage() = this?.takeIf { CardProviderEnum.entries.map(CardProviderEnum::name).contains(it) }?.let {
    CardProviderEnum.valueOf(this).toCardProviderImage()
} ?: R.drawable.icon_card

fun String.containsIgnoreCase(term: String) = lowercase().contains(term.lowercase())