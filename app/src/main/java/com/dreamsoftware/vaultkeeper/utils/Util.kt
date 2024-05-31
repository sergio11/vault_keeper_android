package com.dreamsoftware.vaultkeeper.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> oneShotFlow() = MutableSharedFlow<T>(
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)


fun Modifier.clickWithRipple(bounded: Boolean = true, onClick: () -> Unit) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = bounded),
        onClick = { onClick() }
    )
}

val LocalSnackbar = compositionLocalOf<(String) -> Unit> { { } }
