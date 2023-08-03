package com.mindinventory.fabcardreveal.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.scale

@Composable
fun Modifier.scaleAnimation(
    transitionProgress: State<Float>,
    initialScale: Float = 0f,
    finalScale: Float = 1f
): Modifier {
    val scale = Lerp(initialScale, finalScale, transitionProgress.value)
    Log.d("TAG", "!@# scaleAnimation value: ${transitionProgress.value}")
    return drawWithCache {
        onDrawWithContent {
            scale(scale, scale) {
                this@onDrawWithContent.drawContent()
            }
        }
    }
}