package com.mindinventory.fabcardreveal.utils

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.debugInspectorInfo
import kotlin.math.sqrt

/**
 *  A modifier that clips the composable content with an animated circle, expanding or shrinking
 *  whenever [visible] changes.
 *
 * For more control over the transition, check out the method's overload, allowing the use of a [State]
 * object to manage the progress of the reveal animation.
 *
 *  By default, the circle is centered in the content, but custom positions may be specified using
 *  [revealFrom]. Specified offsets should be between 0 (left/top) and 1 (right/bottom).
 * */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.miCircularReveal(
    visible: Boolean,
    revealFrom: Offset = Offset(0.5f, 0.5f),
): Modifier = composed(
    factory = {
        val factor = updateTransition(visible, label = "Visibility")
            .animateFloat(label = "revealFactor") { if (it) 1f else 0f }

        miCircularReveal(factor, revealFrom)
    },
    inspectorInfo = debugInspectorInfo {
        name = "circularReveal"
        properties["visible"] = visible
        properties["revealFrom"] = revealFrom
    }
)

/**
 * A custom "mi" modifier that clips the composable content using a circular shape.
 *
 * The radius of the circle is determined by the [transitionProgress].
 *
 * The progress values must fall within the range of 0 to 1.
 *
 * The circle is naturally centered in the content by default, but you have the flexibility to specify
 * custom positions using [revealFrom]. The specified offsets should be within the range of 0 (left/top) to 1 (right/bottom).
 **/
fun Modifier.miCircularReveal(
    transitionProgress: State<Float>,
    revealFrom: Offset = Offset(0.5f, 0.5f)
): Modifier {
    return drawWithCache {
        val drawPath = Path()
        val crCenter = revealFrom.mapTo(size)
        val crRadius = assessRadius(revealFrom, size)

        drawPath.addOval(Rect(crCenter, crRadius * transitionProgress.value))

        onDrawWithContent {
            clipPath(drawPath) { this@onDrawWithContent.drawContent() }
        }
    }
}

private fun assessRadius(normalizedOrigin: Offset, size: Size) = with(normalizedOrigin) {
    val x = ((if (x > 0.5f) x else 1 - x) * size.width)
    val y = ((if (y > 0.5f) y else 1 - y) * size.height)

    sqrt(x * x + y * y)
}

private fun Offset.mapTo(size: Size): Offset {
    return Offset(x * size.width, y * size.height)
}