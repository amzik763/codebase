package com.amzi.codebase.utility

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay
import kotlin.time.Duration

@Composable
fun AnimatedDp(initialValue: Dp, finalValue: Dp, duration: Int):Dp{

    val offset by produceState(initialValue = initialValue) {
//      delay(200)    //optional delay added here
        value = finalValue
    }

    val animatedValue by animateDpAsState(targetValue = offset,
        animationSpec = tween(durationMillis = duration, easing = EaseIn),
        label = "")

    return animatedValue

}