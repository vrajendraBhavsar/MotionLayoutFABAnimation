package com.inkindpro.motionlayoutfabanimation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import circularReveal
import com.inkindpro.motionlayoutfabanimation.ui.theme.GolderYellow
import com.inkindpro.motionlayoutfabanimation.ui.theme.MotionLayoutFABAnimationTheme
import kotlinx.coroutines.delay
import java.util.EnumSet

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutFABAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        FABAnimation()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun FABAnimation() {
    val context = LocalContext.current

    //Animate button method
    var animateButton by remember { mutableStateOf(false) } //To manage the state of a FAB button
    var circularRevealAnimation by remember { mutableStateOf(false) }   //To handle circular reveal animation

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

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            circularRevealAnimation = !circularRevealAnimation
        }
        .circularReveal(
//                visible = circularButton,
            transitionProgress = progressState, revealFrom = Offset(0.5f, 0.5f)
        ), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.Red)
                .circularReveal(
//                visible = circularButton,
                    transitionProgress = progressState, revealFrom = Offset(0.5f, 0.5f)
                )
        )
    }

    /**
    * To manage circular reveal animation based on the state of the FAB button
    **/
    if (animateButton) {
        Log.d("TAG", "!@# FABAnimation: $circularRevealAnimation")
        LaunchedEffect(key1 = Unit) {
            Log.d("TAG", "!@# FABAnimation: LaunchedEffect:: $circularRevealAnimation")
            delay(1000)
            circularRevealAnimation = true
        }
    } else {
        LaunchedEffect(key1 = Unit) {
            Log.d("TAG", "!@# FABAnimation: LaunchedEffect:: $circularRevealAnimation")
//            delay(1000)
            circularRevealAnimation = false
        }
    }

    MotionLayout(
        motionScene = MotionScene(motionScene),
        progress = animationProgress,
        debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),    // To enable debug mode
        modifier = Modifier.fillMaxSize()/*            .clickable {
                circularButton = !circularButton
            }
            .circularReveal(
//                visible = circularButton,
                transitionProgress = progressState,
                revealFrom = Offset(0.5f, 0.5f)
            )*/
    ) {
        //Button - 1
        Button(
            onClick = {
                animateButton = !animateButton
//                circularButton = !circularButton
            },
            colors = ButtonDefaults.buttonColors(containerColor = GolderYellow),
            modifier = Modifier.layoutId("btn_coffee"),
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_coffee),
                    contentDescription = "Coffee cup",
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FABAnimationPreview() {
    MotionLayoutFABAnimationTheme {
        FABAnimation()
    }
}