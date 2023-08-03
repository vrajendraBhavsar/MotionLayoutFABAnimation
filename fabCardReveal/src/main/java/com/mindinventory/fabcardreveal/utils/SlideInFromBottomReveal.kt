package com.mindinventory.fabcardreveal.utils

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.translate

fun Modifier.slideInFromBottomAnimation(
    transitionProgress: State<Float>
): Modifier {
    return drawWithCache {
        onDrawWithContent {
            val offsetY = size.height * (1f - transitionProgress.value)
            translate(0f, offsetY) {
                this@onDrawWithContent.drawContent()
            }
        }
    }
}