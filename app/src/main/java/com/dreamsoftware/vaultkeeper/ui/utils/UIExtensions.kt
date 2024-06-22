package com.dreamsoftware.vaultkeeper.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.StringRes
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
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


fun Context.showBiometricPrompt(
    @StringRes titleRes: Int,
    @StringRes subtitleRes: Int,
    @StringRes negativeButtonTextRes: Int,
    onSuccess: (BiometricPrompt.AuthenticationResult) -> Unit,
    onError: (Int, CharSequence) -> Unit = { _, _ -> },
    onFailure: () -> Unit = {}
) {
    val executor = ContextCompat.getMainExecutor(this)
    findActivity().safeCast<FragmentActivity>()?.let {
        val biometricPrompt = BiometricPrompt(it, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess(result)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onFailure()
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(titleRes))
            .setSubtitle(getString(subtitleRes))
            .setNegativeButtonText(getString(negativeButtonTextRes))
            .build()

        biometricPrompt.authenticate(promptInfo)
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


fun CardProviderEnum.toCardProviderBrush(context: Context): Brush {
    val (startColorRes, endColorRes) = when (this) {
        CardProviderEnum.VISA -> R.color.visa_start to R.color.visa_end
        CardProviderEnum.MASTERCARD -> R.color.mastercard_start to R.color.mastercard_end
        CardProviderEnum.AMERICAN_EXPRESS -> R.color.american_express_start to R.color.american_express_end
        CardProviderEnum.RUPAY -> R.color.rupay_start to R.color.rupay_end
        CardProviderEnum.DINERS_CLUB -> R.color.diners_club_start to R.color.diners_club_end
        CardProviderEnum.OTHER -> R.color.other_start to R.color.other_end
    }
    val startColor = ContextCompat.getColor(context, startColorRes)
    val endColor = ContextCompat.getColor(context, endColorRes)
    return Brush.verticalGradient(listOf(Color(startColor), Color(endColor)))
}

fun String?.toCardProviderImage() = this?.takeIf { CardProviderEnum.entries.map(CardProviderEnum::name).contains(it) }?.let {
    CardProviderEnum.valueOf(this).toCardProviderImage()
} ?: R.drawable.icon_card

fun String.containsIgnoreCase(term: String) = lowercase().contains(term.lowercase())

inline fun <reified T> Any?.safeCast(): T? = (this as? T)

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
