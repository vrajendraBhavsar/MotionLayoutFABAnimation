package com.mindinventory.fabcardreveal

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import com.mindinventory.fabcardreveal.utils.miCircularReveal
import com.mindinventory.motionlayoutfabanimation.ui.theme.MattePurple
import kotlinx.coroutines.delay
import java.util.EnumSet

@OptIn(ExperimentalMotionApi::class)
@Composable
fun CircularRevealAnimation(
    modifier: Modifier = Modifier,
    cardComposable: @Composable (() -> Unit)? = null,
    fabComposable: @Composable (() -> Unit)? = null,
    circularRevealAnimationVal: MutableState<Boolean> = remember { mutableStateOf(false) },
    animateButtonVal: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    /*val context = LocalContext.current
    var animateButton by remember { mutableStateOf(false) } //To manage the state of a FAB button
    LaunchedEffect(key1 = animateButtonVal) {
        animateButton = animateButtonVal
    }
    var circularRevealAnimation by remember { mutableStateOf(false) }   //To handle circular reveal animation
//    circularRevealAnimation = circularRevealAnimationVal      //????????????????????????????????????????????????????? ..Once project get set up, need to check if this way can we close the card
    LaunchedEffect(key1 = circularRevealAnimationVal) {
        animateButton = circularRevealAnimationVal
    }*/

    val context = LocalContext.current
    var animateButton by animateButtonVal
    var circularRevealAnimation by circularRevealAnimationVal

    //Button animation progress
    val animationProgress by animateFloatAsState(
        targetValue = if (animateButton) 1f else 0f,
        animationSpec = tween(1000) // keeping animation specific duration to 1 sec
    )
    val progressState = animateFloatAsState(
        targetValue = if (circularRevealAnimation) 1f else 0f,
        animationSpec = tween(1000) // keeping animation specific duration to 1 sec
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
                .background(MattePurple.copy(alpha = 0.8f))
                .miCircularReveal(
                    transitionProgress = progressState, revealFrom = Offset(0.5f, 0.5f)
                )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .miCircularReveal(transitionProgress = progressState, revealFrom = Offset(0.5f, 0.5f)),
        contentAlignment = Alignment.BottomCenter
    ) {
        //FIXME: Here we'll pass composable for Card content
        cardComposable?.let { it() }
    }

    /**
     * To handle closing circular reveal animation
     ***/
    if (!circularRevealAnimation) {
        LaunchedEffect(key1 = Unit) {
            delay(1000)
            animateButton = false
        }
    }

    /**
     * To manage circular reveal animation based on the state of the FAB button
     **/
    if (animateButton) {
        LaunchedEffect(key1 = Unit) {
            delay(1000)
            circularRevealAnimation = true
        }
    } else {
        LaunchedEffect(key1 = Unit) {
            delay(1000)
            circularRevealAnimation = false
        }
    }

    MotionLayout(
        motionScene = MotionScene(motionScene),
        progress = animationProgress,
        debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),    // To enable debug mode
        modifier = Modifier.fillMaxSize()
    ) {
        fabComposable?.let { it() }
    }
}