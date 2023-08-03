package com.mindinventory.fabcardreveal.utils


import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.*
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


//Working
/*fun Modifier.slideInFromBottomAnimation(
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
}*/

//Working
/*@Composable
fun Modifier.scaleAnimation(
    transitionProgress: State<Float>,
    initialScale: Float = 0f,
    finalScale: Float = 1f
): Modifier {
    val scale = lerp(initialScale, finalScale, transitionProgress.value)
    Log.d("TAG", "!@# scaleAnimation value: ${transitionProgress.value}")
    return drawWithCache {
        onDrawWithContent {
            scale(scale, scale) {
                this@onDrawWithContent.drawContent()
            }
        }
    }
}*/

//Working
/*@Composable
fun Modifier.rotateAnimation(
    transitionProgress: State<Float>,
    initialRotationDegrees: Float = 0f,
    finalRotationDegrees: Float = 360f
): Modifier {
    val rotationDegrees = lerp(initialRotationDegrees, finalRotationDegrees, transitionProgress.value)
    Log.d("TAG", "!@# scaleAnimation rotate value: ${transitionProgress.value}")
    return drawWithCache {
        onDrawWithContent {
            rotate(rotationDegrees) {
                this@onDrawWithContent.drawContent()
            }
        }
    }
}*/


/*@Composable
fun lerp(startValue: Float, endValue: Float, fraction: Float): Float {
    val animatedValue = animateFloatAsState(
        targetValue = if (fraction <= 0f) startValue else if (fraction >= 1f) endValue else startValue + fraction * (endValue - startValue),
        label = ""
    ).value
    return animatedValue
}*/

/*fun Modifier.fadeInOutAnimation(
    transitionProgress: State<Float>
): Modifier {
    return drawWithCache {
        onDrawWithContent {
            val alpha = transitionProgress.value
            drawContent()
            drawRect(Color.Black.copy(alpha = 1f - alpha))
        }
    }
}*/

//xxx
/*@Composable
fun Modifier.fadeInOutAnimation(isVisible: Boolean): Modifier {
    val alpha by animateFloatAsState(targetValue = if (isVisible) 1f else 0f)
    return drawWithCache {
        onDrawWithContent {
            drawContent(modifier = alpha(alpha))
        }
    }
}*/

//Working a bit, but need to manage alpha
/*fun Modifier.crossfadeTransition(
    transitionProgress: State<Float>
): Modifier {
    return drawWithCache {
        onDrawWithContent {
            this@onDrawWithContent.drawContent()

            val alpha = 1 - transitionProgress.value
            drawRect(Color.Black.copy(alpha = alpha))
        }
    }
}*/


// Not working
/*@Composable
fun Modifier.slideInOutAnimation(isVisible: Boolean, slideInFrom: Offset, slideOutTo: Offset): Modifier {
    val offset by animateOffsetAsState(
        if (isVisible) slideInFrom else slideOutTo,
        finishedListener = { offset -> *//* handle animation completion if needed *//* },
        label = ""
    )

    return drawWithCache {
        onDrawWithContent {
            drawContent()
            translate(left = offset.x, top = offset.y) {
                this@onDrawWithContent.drawContent()
            }
        }
    }
}*/


/*fun Modifier.miHeartReveal(
    transitionProgress: State<Float>,
    revealFrom: Offset = Offset(0.5f, 0.5f)
): Modifier {
    return drawWithCache {
        val drawPath = Path()
        val crCenter = revealFrom.mapTo(size)
        val crRadius = assessRadius(revealFrom, size)

        val heartPath = Path().apply {
            moveTo(crCenter.x, crCenter.y - crRadius * transitionProgress.value)

            val controlX1 = crCenter.x + crRadius * 0.5f
            val controlX2 = crCenter.x
            val controlY1 = crCenter.y - crRadius * 0.75f * transitionProgress.value
            val controlY2 = crCenter.y - crRadius * 0.75f * transitionProgress.value

            val endX = crCenter.x
            val endY = crCenter.y + crRadius * transitionProgress.value

            cubicTo(controlX1, controlY1, controlX2, controlY2, endX, endY)

            val controlX3 = crCenter.x - crRadius * 0.5f
            val controlX4 = crCenter.x
            val controlY3 = crCenter.y - crRadius * 0.75f * transitionProgress.value
            val controlY4 = crCenter.y - crRadius * 0.75f * transitionProgress.value

            cubicTo(controlX3, controlY3, controlX4, controlY4, crCenter.x, crCenter.y - crRadius * transitionProgress.value)

            close()
        }

        drawPath.addPath(heartPath)

        onDrawWithContent {
            clipPath(drawPath) { this@onDrawWithContent.drawContent() }
        }
    }
}*/


/*fun Modifier.miEllipticalReveal(
    transitionProgress: State<Float>,
    revealFrom: Offset = Offset(0.5f, 0.5f)
): Modifier {
    return drawWithCache {
        val drawPath = Path()
        val crCenter = revealFrom.mapTo(size)
        val crRadiusX = assessRadius(Offset(revealFrom.x, ), size.width)
        val crRadiusY = assessRadius(revealFrom.y, size.height)

        drawPath.addOval(
            Rect(
            crCenter.x - crRadiusX * transitionProgress.value,
            crCenter.y - crRadiusY * transitionProgress.value,
            crCenter.x + crRadiusX * transitionProgress.value,
            crCenter.y + crRadiusY * transitionProgress.value
        )
        )

        onDrawWithContent {
            clipPath(drawPath) { this@onDrawWithContent.drawContent() }
        }
    }
}*/


/*fun Modifier.miCardShapedDiamondReveal(
    transitionProgress: State<Float>,
    revealFrom: Offset = Offset(0.5f, 0.5f)
): Modifier {
    return drawWithCache {
        val drawPath = Path()
        val crCenter = revealFrom.mapTo(size)
        val crRadius = assessRadius(revealFrom, size)

        val diamondPath = Path().apply {
            moveTo(crCenter.x, crCenter.y - crRadius * transitionProgress.value)
            lineTo(crCenter.x + crRadius * transitionProgress.value, crCenter.y)
            lineTo(crCenter.x, crCenter.y + crRadius * transitionProgress.value)
            lineTo(crCenter.x - crRadius * transitionProgress.value, crCenter.y)
            close()
        }

        onDrawWithContent {
            this@onDrawWithContent.drawContent()

            clipPath(diamondPath) {
                clipRect(0f, 0f, right = size.width, bottom = size.height) {}// Clipping to the card bounds
            }
        }
    }
}*/


/*fun Modifier.miDiamondReveal(
    transitionProgress: State<Float>,
    revealFrom: Offset = Offset(0.5f, 0.5f)
): Modifier {
    return drawWithContent { content ->
        val drawPath = Path()
        val crCenter = revealFrom.mapTo(size)
        val crRadius = assessRadius(revealFrom, size)

        val diamondPath = Path().apply {
            moveTo(crCenter.x, crCenter.y - crRadius)
            lineTo(crCenter.x + crRadius, crCenter.y)
            lineTo(crCenter.x, crCenter.y + crRadius)
            lineTo(crCenter.x - crRadius, crCenter.y)
            close()
        }

        onDrawBehind {
            clipPath(diamondPath) {
                this@onDrawBehind.drawContent()
            }
        }

        onDraw {
            val revealSize = sqrt(size.width * size.width + size.height * size.height)
            val revealOffset = (revealSize - crRadius * transitionProgress.value) / 2

            withTransform({
                // Translate the canvas to the center of the diamond
                translate(crCenter.x, crCenter.y)
            }) {
                clipRect(-revealOffset, -revealOffset, revealOffset, revealOffset)
                drawPath(diamondPath, color = Color.Blue)
            }
        }
    }
}*/


/*fun Modifier.miDiamondReveal(
    transitionProgress: State<Float>,
    revealFrom: Offset = Offset(0.5f, 0.5f)
): Modifier {
    return drawWithCache {
        val drawPath = Path()
        val crCenter = revealFrom.mapTo(size)
        val crRadius = assessRadius(revealFrom, size)

        val diamondPath = Path().apply {
            moveTo(crCenter.x, crCenter.y - crRadius * transitionProgress.value)
            lineTo(crCenter.x + crRadius * transitionProgress.value, crCenter.y)
            lineTo(crCenter.x, crCenter.y + crRadius * transitionProgress.value)
            lineTo(crCenter.x - crRadius * transitionProgress.value, crCenter.y)
            close()
        }

        drawPath.addPath(diamondPath)

        onDrawWithContent {
            clipPath(drawPath) { this@onDrawWithContent.drawContent() }
        }
    }
}*/
