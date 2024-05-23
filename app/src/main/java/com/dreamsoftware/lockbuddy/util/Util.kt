package com.dreamsoftware.lockbuddy.util

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.dreamsoftware.lockbuddy.ui.destinations.CardScreenDestination
import com.dreamsoftware.lockbuddy.ui.destinations.Destination
import com.dreamsoftware.lockbuddy.ui.destinations.IntroScreenDestination
import com.dreamsoftware.lockbuddy.ui.destinations.MasterKeyScreenDestination
import com.dreamsoftware.lockbuddy.ui.destinations.PasswordScreenDestination
import com.dreamsoftware.lockbuddy.ui.destinations.UnlockScreenDestination
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> oneShotFlow() = MutableSharedFlow<T>(
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

fun Destination.shouldShowBottomBar(): Boolean {

    return (this !in listOf(
        IntroScreenDestination,
        UnlockScreenDestination,
        MasterKeyScreenDestination,
        PasswordScreenDestination,
        CardScreenDestination
    ))
}

fun Modifier.clickWithRipple(bounded: Boolean = true, onClick: () -> Unit) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = bounded),
        onClick = { onClick() }
    )
}

val LocalSnackbar = compositionLocalOf<(String) -> Unit> { { } }

fun isBiometricSupported(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
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
