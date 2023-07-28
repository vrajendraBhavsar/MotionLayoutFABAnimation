package com.inkindpro.motionlayoutfabanimation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import circularReveal
import com.inkindpro.motionlayoutfabanimation.ui.theme.Magnolia
import com.inkindpro.motionlayoutfabanimation.ui.theme.MattePurple
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

    Box(
        modifier = Modifier
            .fillMaxSize()/*.clickable {
            circularRevealAnimation = !circularRevealAnimation
        }*/
            .circularReveal(
//                visible = circularButton,
                transitionProgress = progressState, revealFrom = Offset(0.5f, 0.5f)
            ), contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(350.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(MattePurple)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    Text(
                        text = stringResource(id = R.string.circular_reveal_title),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold
                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(text = stringResource(id = R.string.circular_reveal_sub_title), fontSize = 24.sp, fontWeight = FontWeight.Normal)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.circular_reveal_body_copy_text),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Button(
                    onClick = {
                        circularRevealAnimation = false
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Magnolia),
                ) {
                    Text(
                        text = stringResource(id = R.string.circular_reveal_btn_close),
                        fontSize = 16.sp,
                        color = MattePurple,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }

    /**
     * To handle closing circular reveal animation
     ***/
    if (!circularRevealAnimation) {
        LaunchedEffect(key1 = Unit) {
            Log.d("TAG", "!@# FABAnimation: LaunchedEffect:: IFF $circularRevealAnimation")
            delay(1000)
            animateButton = false
        }
    }

    /**
     * To manage circular reveal animation based on the state of the FAB button
     **/
    if (animateButton) {
        Log.d("TAG", "!@# FABAnimation: $circularRevealAnimation")
        LaunchedEffect(key1 = Unit) {
            Log.d("TAG", "!@# FABAnimation: LaunchedEffect:: IFF $circularRevealAnimation")
            delay(1000)
            circularRevealAnimation = true
        }
    } else {
        LaunchedEffect(key1 = Unit) {
            Log.d("TAG", "!@# FABAnimation: LaunchedEffect:: ELSE $circularRevealAnimation")
            delay(1000)
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
        //Animating FAB button
        Button(
            onClick = {
                animateButton = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Magnolia),
            modifier = Modifier.layoutId("btn_coffee"),
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_coffee),
                contentDescription = "Coffee cup",
                modifier = Modifier
//                        .padding(2.dp)
//                    .fillMaxSize()
            )

            /*Row(
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
//                        .padding(2.dp)
                        .fillMaxSize()
                )
            }*/
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