package com.mindinventory.fabcardreveal.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable

@Composable
fun Lerp(startValue: Float, endValue: Float, fraction: Float): Float {
    return animateFloatAsState(
        targetValue = if (fraction <= 0f) startValue else if (fraction >= 1f) endValue else startValue + fraction * (endValue - startValue),
        label = ""
    ).value
}