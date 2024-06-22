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

fun String?.toAccountProviderImage(context: Context) = this?.let {
    with(context) {
        when {
            containsIgnoreCase(getString(R.string.amazon_prime)) -> R.drawable.icon_amazon_prime_video
            containsIgnoreCase(getString(R.string.behance)) -> R.drawable.icon_behance
            containsIgnoreCase(getString(R.string.discord)) -> R.drawable.icon_discord
            containsIgnoreCase(getString(R.string.dribbble)) -> R.drawable.icon_dribbble
            containsIgnoreCase(getString(R.string.facebook)) -> R.drawable.icon_facebook
            containsIgnoreCase(getString(R.string.github)) -> R.drawable.icon_github
            containsIgnoreCase(getString(R.string.gmail)) -> R.drawable.icon_gmail
            containsIgnoreCase(getString(R.string.instagram)) -> R.drawable.icon_instagram
            containsIgnoreCase(getString(R.string.linkedin)) -> R.drawable.icon_linkedin
            containsIgnoreCase(getString(R.string.medium)) -> R.drawable.icon_medium
            containsIgnoreCase(getString(R.string.messenger)) -> R.drawable.icon_messenger
            containsIgnoreCase(getString(R.string.netflix)) -> R.drawable.icon_netflix
            containsIgnoreCase(getString(R.string.pinterest)) -> R.drawable.icon_pinterest
            containsIgnoreCase(getString(R.string.quora)) -> R.drawable.icon_quora
            containsIgnoreCase(getString(R.string.reddit)) -> R.drawable.icon_reddit
            containsIgnoreCase(getString(R.string.snapchat)) -> R.drawable.icon_snapchat
            containsIgnoreCase(getString(R.string.spotify)) -> R.drawable.icon_spotify
            containsIgnoreCase(getString(R.string.stackoverflow)) -> R.drawable.icon_stackoverflow
            containsIgnoreCase(getString(R.string.tumblr)) -> R.drawable.icon_tumblr
            containsIgnoreCase(getString(R.string.twitter)) -> R.drawable.icon_twitterx
            containsIgnoreCase(getString(R.string.whatsapp)) -> R.drawable.icon_whatsapp
            containsIgnoreCase(getString(R.string.wordpress)) -> R.drawable.icon_wordpress
            containsIgnoreCase(getString(R.string.youtube)) -> R.drawable.icon_youtube
            else -> R.drawable.icon_others
        }
    }
} ?: R.drawable.icon_others

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

fun String?.toAccountProviderBrush(context: Context): Brush = this?.let {
    val (startColorRes, endColorRes) = with(context) {
        when {
            containsIgnoreCase(getString(R.string.amazon_prime)) -> R.color.amazon_prime_start to R.color.amazon_prime_end
            containsIgnoreCase(getString(R.string.behance)) -> R.color.behance_start to R.color.behance_end
            containsIgnoreCase(getString(R.string.discord)) -> R.color.discord_start to R.color.discord_end
            containsIgnoreCase(getString(R.string.dribbble)) -> R.color.dribbble_start to R.color.dribbble_end
            containsIgnoreCase(getString(R.string.facebook)) -> R.color.facebook_start to R.color.facebook_end
            containsIgnoreCase(getString(R.string.github)) -> R.color.github_start to R.color.github_end
            containsIgnoreCase(getString(R.string.gmail)) -> R.color.gmail_start to R.color.gmail_end
            containsIgnoreCase(getString(R.string.instagram)) -> R.color.instagram_start to R.color.instagram_end
            containsIgnoreCase(getString(R.string.linkedin)) -> R.color.linkedin_start to R.color.linkedin_end
            containsIgnoreCase(getString(R.string.medium)) -> R.color.medium_start to R.color.medium_end
            containsIgnoreCase(getString(R.string.messenger)) -> R.color.messenger_start to R.color.messenger_end
            containsIgnoreCase(getString(R.string.netflix)) -> R.color.netflix_start to R.color.netflix_end
            containsIgnoreCase(getString(R.string.pinterest)) -> R.color.pinterest_start to R.color.pinterest_end
            containsIgnoreCase(getString(R.string.quora)) -> R.color.quora_start to R.color.quora_end
            containsIgnoreCase(getString(R.string.reddit)) -> R.color.reddit_start to R.color.reddit_end
            containsIgnoreCase(getString(R.string.snapchat)) -> R.color.snapchat_start to R.color.snapchat_end
            containsIgnoreCase(getString(R.string.spotify)) -> R.color.spotify_start to R.color.spotify_end
            containsIgnoreCase(getString(R.string.stackoverflow)) -> R.color.stackoverflow_start to R.color.stackoverflow_end
            containsIgnoreCase(getString(R.string.tumblr)) -> R.color.tumblr_start to R.color.tumblr_end
            containsIgnoreCase(getString(R.string.twitter)) -> R.color.twitter_start to R.color.twitter_end
            containsIgnoreCase(getString(R.string.whatsapp)) -> R.color.whatsapp_start to R.color.whatsapp_end
            containsIgnoreCase(getString(R.string.wordpress)) -> R.color.wordpress_start to R.color.wordpress_end
            containsIgnoreCase(getString(R.string.youtube)) -> R.color.youtube_start to R.color.youtube_end
            else -> R.color.others_start to R.color.others_end
        }
    }
    val startColor = ContextCompat.getColor(context, startColorRes)
    val endColor = ContextCompat.getColor(context, endColorRes)
    Brush.verticalGradient(listOf(Color(startColor), Color(endColor)))
} ?: Brush.verticalGradient(
    colors = listOf(
        Color(ContextCompat.getColor(context, R.color.others_start)),
        Color(ContextCompat.getColor(context, R.color.others_end))
    )
)

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
