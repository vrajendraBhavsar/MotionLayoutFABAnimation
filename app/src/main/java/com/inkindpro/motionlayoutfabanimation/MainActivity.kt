package com.inkindpro.motionlayoutfabanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import circularReveal
import com.inkindpro.motionlayoutfabanimation.ui.theme.Beige
import com.inkindpro.motionlayoutfabanimation.ui.theme.GolderYellow
import com.inkindpro.motionlayoutfabanimation.ui.theme.Magnolia
import com.inkindpro.motionlayoutfabanimation.ui.theme.MattePurple
import com.inkindpro.motionlayoutfabanimation.ui.theme.MotionLayoutFABAnimationTheme
import com.inkindpro.motionlayoutfabanimation.ui.theme.StrongRed
import kotlinx.coroutines.delay
import java.util.EnumSet

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutFABAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Beige
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
    val context = LocalContext.current as MainActivity
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

    //Overlay in the bg of circular reveal
    if (circularRevealAnimation) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MattePurple.copy(alpha = 0.8f))
                .circularReveal(
                    transitionProgress = progressState, revealFrom = Offset(0.5f, 0.5f)
                )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .circularReveal(
                transitionProgress = progressState, revealFrom = Offset(0.5f, 0.5f)
            ), contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(brush = Brush.verticalGradient(listOf(MattePurple, StrongRed))) // Define your vertical gradient colors here
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                val uriHandler = LocalUriHandler.current
                val uri = stringResource(id = R.string.circular_reveal_body_copy_link)

                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    Text(
                        text = stringResource(id = R.string.circular_reveal_title),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Beige,
                        lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    val gameLink = buildAnnotatedString {
                        append(text = stringResource(id = R.string.circular_reveal_body_copy_link_text_1))
                        append(text = " ")
                        pushStringAnnotation(
                            tag = stringResource(id = R.string.circular_reveal_body_copy_link_text_2),
                            annotation = stringResource(id = R.string.circular_reveal_body_copy_link)
                        )
                        withStyle(
                            style = SpanStyle(
                                color = GolderYellow,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(stringResource(id = R.string.circular_reveal_body_copy_link_text_2))
                        }
                        append(text = " ")
                        append(text = stringResource(id = R.string.circular_reveal_body_copy_link_text_3))
                        pop()
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    ClickableText(
                        text = gameLink,
                        style = TextStyle(
                        color = Beige,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
//                            fontFamily = FontFamily(Font(R.font.yourFontFamily)),
                            textAlign = TextAlign.Start),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            uriHandler.openUri(uri) //Opening a link into the supportive browser using URI Handler
                        })

                    Text(
                        text = stringResource(id = R.string.circular_reveal_body_copy_text),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Beige
                    )
                }

                Row {
                    Button(
                        onClick = {
                            circularRevealAnimation = false
                        },
                        modifier = Modifier
                            .padding(start = 20.dp, end = 10.dp, bottom = 20.dp)
                            .fillMaxWidth()
                            .aspectRatio(3f) // Adjust the aspect ratio to your preference
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Beige),
                    ) {
                        Text(
                            text = stringResource(id = R.string.circular_reveal_btn_close),
                            fontSize = 16.sp,
                            color = MattePurple,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Button(
                        onClick = {
                            uriHandler.openUri(uri)
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp, bottom = 20.dp)
                            .fillMaxWidth()
                            .aspectRatio(3f) // Adjust the aspect ratio to your preference
                            .weight(1f)
                        ,
                        colors = ButtonDefaults.buttonColors(containerColor = GolderYellow),
                    ) {
                        Text(
                            text = stringResource(id = R.string.circular_reveal_btn_playstore_link),
                            fontSize = 16.sp,
                            color = MattePurple,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
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
        //Animating Bal hanuman standing pose image
        Image(
            modifier = Modifier
                .layoutId("img_hanuman_standing")
                .clickable { animateButton = true },
            painter = painterResource(id = R.drawable.ic_hanuman_standing_pose),
            contentDescription = "Hanuman standing"
        )
        //Animating Bal hanuman victory pose image
        Image(
            modifier = Modifier
                .layoutId("img_hanuman_pose")
                .clickable { animateButton = true },
            painter = painterResource(id = R.drawable.ic_hanuman_thumps_up),
            contentDescription = "Hanuman victory pose"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FABAnimationPreview() {
    MotionLayoutFABAnimationTheme {
        FABAnimation()
    }
}