package com.nextmacrosystem.reciclapp.presentation.animations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

private const val ANIMATION_TIME = 200

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnimatedTransitionDialog(
    onDismissRequest: () -> Unit,
    contentAlignment: Alignment = Alignment.Center,
    clickPosition: Offset? = null,
    content: @Composable () -> Unit
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            animateTrigger.value = true
        }
    }
    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Box(
            contentAlignment = contentAlignment,
            modifier = Modifier.fillMaxWidth()
        ) {
            AnimatedScaleInTransition(
                visible = animateTrigger.value,
                positionX = clickPosition?.x,
                positionY = clickPosition?.y,
                content = {
                    content()
                })
        }
    }
}

@Composable
internal fun AnimatedScaleInTransition(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
    positionX: Float? = null,
    positionY: Float? = null,
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(ANIMATION_TIME),
            transformOrigin = if (positionX != null && positionY != null) {
                TransformOrigin(positionX, positionY)
            } else TransformOrigin.Center
        ),
        exit = scaleOut(
            animationSpec = tween(ANIMATION_TIME),
            transformOrigin = if (positionX != null && positionY != null) {
                TransformOrigin(positionX, positionY)
            } else TransformOrigin.Center
        ),
        content = content
    )
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun startDismissWithExitAnimation(
    animateTrigger: MutableState<Boolean>,
    onDismissRequest: () -> Unit
) {
    animateTrigger.value = false
    delay(duration = Duration.ofMillis(ANIMATION_TIME.toLong()))
    onDismissRequest()
}