package com.dreamsoftware.lockbuddy.ui.core.components

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.EventLogger

@SuppressLint("OpaqueUnitKey")
@Composable
fun CommonVideoBackground(
    @RawRes videoResourceId: Int
) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context)
        .build()
        .apply {
            addAnalyticsListener(EventLogger())
            setMediaItem(MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(videoResourceId)))
            prepare()
            repeatMode = Player.REPEAT_MODE_ALL
            playWhenReady = false
        }
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    DisposableEffect(AndroidView(factory = {
        StyledPlayerView(context).apply {
            player = exoPlayer
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            setBackgroundColor(Color.Black.toArgb())
            hideController()
        }
    }, modifier = Modifier.fillMaxSize())) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.play()
                }
                else -> {}
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.release()
            lifecycle.removeObserver(observer)
        }
    }
}