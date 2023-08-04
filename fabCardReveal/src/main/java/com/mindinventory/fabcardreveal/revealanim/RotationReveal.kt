package com.mindinventory.fabcardreveal.revealanim

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.rotate
import com.mindinventory.fabcardreveal.utils.Lerp

@Composable
fun Modifier.rotateAnimation(
    transitionProgress: State<Float>,
    initialRotationDegrees: Float = 0f,
    finalRotationDegrees: Float = 360f
): Modifier {
    val rotationDegrees = Lerp(initialRotationDegrees, finalRotationDegrees, transitionProgress.value)
    Log.d("TAG", "!@# scaleAnimation rotate value: ${transitionProgress.value}")
    return drawWithCache {
        onDrawWithContent {
            rotate(rotationDegrees) {
                this@onDrawWithContent.drawContent()
            }
        }
    }
}