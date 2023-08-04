package com.mindinventory.fabcardreveal.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import com.mindinventory.fabcardreveal.R
import com.mindinventory.fabcardreveal.revealanim.fadeInOutAnimation
import com.mindinventory.fabcardreveal.revealanim.miCircularReveal
import com.mindinventory.fabcardreveal.revealanim.rotateAnimation
import com.mindinventory.fabcardreveal.revealanim.scaleAnimation
import com.mindinventory.fabcardreveal.revealanim.slideInFromBottomAnimation
import com.mindinventory.fabcardreveal.ui.theme.MattePurple
import com.mindinventory.fabcardreveal.ui.theme.OffWhite
import com.mindinventory.fabcardreveal.utils.AnimationType
import kotlinx.coroutines.delay
import java.util.EnumSet

@OptIn(ExperimentalMotionApi::class, ExperimentalUnitApi::class)
@Composable
fun CircularRevealAnimation(
    cardComposable: @Composable (() -> Unit)? = null,
    fabComposable: @Composable (() -> Unit)? = null,
    circularRevealAnimationVal: MutableState<Boolean> = remember { mutableStateOf(false) },
    animateButtonVal: MutableState<Boolean> = remember { mutableStateOf(false) },
    hideFabPostAnimationVal: MutableState<Boolean> = remember { mutableStateOf(false) },
    fabAnimationDur: Int = 1000,
    revealAnimDur: Int = 1000,
    fabCloseDelay: Long = 1000L,    //To adjust closing delay in order to make animation of the Circular reveal smooth and
    animationType: AnimationType = AnimationType.CIRCULAR_REVEAL,
    overlayBackgroundColor: Color = Color.Black
) {
    val context = LocalContext.current

    var animateButton by animateButtonVal
    var circularRevealAnimation by circularRevealAnimationVal
    val hideFabPostAnimation by hideFabPostAnimationVal

    var isFABVisible by remember { mutableStateOf(true) }

    //Button animation progress
    val animationProgress by animateFloatAsState(
        targetValue = if (animateButton) 1f else 0f,
        animationSpec = tween(fabAnimationDur),    // keeping animation specific duration to 1 sec
        label = "Animation progress"
    )
    val progressState = animateFloatAsState(
        targetValue = if (circularRevealAnimation) 1f else 0f,
        animationSpec = tween(revealAnimDur),    // keeping animation specific duration to 1 sec
        label = "Progress state"
    )

    // To include raw/JSON5-script file
    val motionScene: String = remember {
        context.resources.openRawResource(R.raw.motion_scene_fab)
            .readBytes()    //Reading bytes as we want MotionScene in a String format
            .decodeToString()   //To decode byte array to String
    }

    //Overlay in the bg of circular reveal
    if (circularRevealAnimation) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(overlayBackgroundColor)
                .applyAnimationBasedOnType(
                    superModifier = Modifier,
                    animationType = animationType,
                    transitionProgress = progressState
                )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .applyAnimationBasedOnType(
                superModifier = Modifier,
                animationType = animationType,
                transitionProgress = progressState
            ), contentAlignment = Alignment.BottomCenter
    ) {
        cardComposable?.let {
            if (animateButton) {
                it()
            }
        }
    }

    /**
     * To handle closing circular reveal animation
     ***/
    if (!circularRevealAnimation) {
        LaunchedEffect(key1 = Unit) {
            delay(fabCloseDelay)
            animateButton = false
        }
        LaunchedEffect(key1 = Unit) {
            delay(550)
            if (hideFabPostAnimation) isFABVisible = true
        }
    }

    /**
     * To manage circular reveal animation based on the state of the FAB button
     **/
    if (animateButton) {
        LaunchedEffect(key1 = Unit) {
            delay(1000)
            if (hideFabPostAnimation) isFABVisible = false
            circularRevealAnimation = true
        }
    } else {
        LaunchedEffect(key1 = Unit) {
            delay(1000)
            circularRevealAnimation = false
        }
    }

    if (isFABVisible) {
        MotionLayout(
            motionScene = MotionScene(motionScene),
            progress = animationProgress,
            debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),    // To enable debug mode
            modifier = Modifier.fillMaxSize()
        ) {
            if (fabComposable != null) {
                fabComposable.let { it() }
            } else {
                //Button - 2
                Button(
                    onClick = {
                        if (!circularRevealAnimation) animateButton = !animateButton
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MattePurple),
                    modifier = Modifier
                        .layoutId("img_hanuman_standing")
                        .padding(10.dp),
                ) {
                    Text(
                        text = "+",
                        color = OffWhite,
                        fontSize = TextUnit(value = 40F, type = TextUnitType.Sp),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = {
                        if (!circularRevealAnimation) animateButton = !animateButton
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MattePurple),
                    modifier = Modifier
                        .layoutId("img_hanuman_pose")
                        .padding(25.dp),
                ) {
                    Text(
                        text = "+",
                        color = OffWhite,
                        fontSize = TextUnit(value = 40F, type = TextUnitType.Sp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun Modifier.applyAnimationBasedOnType(
    animationType: AnimationType,
    superModifier: Modifier,
    transitionProgress: State<Float>,
    visible: Boolean = false,   //If true -> transition method, false -> non-transition method,
    initialRotationDegrees: Float = 0f,
    finalRotationDegrees: Float = 360f,
    initialScale: Float = 0f,
    finalScale: Float = 1f
): Modifier {
    return when (animationType) {
        AnimationType.CIRCULAR_REVEAL -> {
            if (visible) {
                this.then(
                    miCircularReveal(
                        visible = visible, revealFrom = Offset(0.5f, 0.5f)
                    )
                )
            } else {
                this.then(
                    miCircularReveal(
                        transitionProgress = transitionProgress, revealFrom = Offset(0.5f, 0.5f)
                    )
                )
            }
        }

        AnimationType.FADE_REVEAL -> {
            this.then(
                fadeInOutAnimation(
                    transitionProgress = transitionProgress
                )
            )
        }

        AnimationType.ROTATION_REVEAL -> {
            this.then(
                rotateAnimation(
                    transitionProgress = transitionProgress,
                    initialRotationDegrees = initialRotationDegrees,
                    finalRotationDegrees = finalRotationDegrees
                )
            )
        }

        AnimationType.SCALE_REVEAL -> {
            this.then(
                scaleAnimation(
                    transitionProgress = transitionProgress,
                    initialScale = initialScale,
                    finalScale = finalScale
                )
            )
        }

        AnimationType.SLIDE_IN_FROM_BOTTOM -> {
            this.then(
                slideInFromBottomAnimation(
                    transitionProgress = transitionProgress
                )
            )
        }
    }.then(superModifier)
}