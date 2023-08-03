package com.mindinventory.fabcardreveal.utils

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color

fun Modifier.fadeInOutAnimation(
    transitionProgress: State<Float>
): Modifier {
    return drawWithCache {
        onDrawWithContent {
            val alpha = transitionProgress.value
            drawContent()
            drawRect(Color.Black.copy(alpha = 1f - alpha))
        }
    }
}