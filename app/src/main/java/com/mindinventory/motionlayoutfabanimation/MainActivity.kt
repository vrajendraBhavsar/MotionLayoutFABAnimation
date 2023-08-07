package com.mindinventory.motionlayoutfabanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.layoutId
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mindinventory.fabcardreveal.R
import com.mindinventory.fabcardreveal.presentation.CircularRevealAnimation
import com.mindinventory.fabcardreveal.ui.theme.Beige
import com.mindinventory.fabcardreveal.ui.theme.GolderYellow
import com.mindinventory.fabcardreveal.ui.theme.MattePurple
import com.mindinventory.fabcardreveal.ui.theme.MotionLayoutFABAnimationTheme
import com.mindinventory.fabcardreveal.ui.theme.StrongRed
import com.mindinventory.fabcardreveal.utils.AnimationType
import com.mindinventory.motionlayoutfabanimation.ui.theme.DarkBlue
import com.mindinventory.motionlayoutfabanimation.ui.theme.GridBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutFABAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Beige
                ) {
                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = MaterialTheme.colorScheme.isLight()

                    // To set the status bar color
                    SideEffect {
                        systemUiController.setSystemBarsColor(
                            color = Color.Black,
                            darkIcons = useDarkIcons
                        )
                    }
                    MainScreenContent()
                    FabCardRevealHandler()
                }
            }
        }
    }
}

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5

@Composable
fun FabCardRevealHandler() {
    val revealAnimation =
        remember { mutableStateOf(false) }  //To handle circular reveal animation
    val animateButton =
        remember { mutableStateOf(false) }  //To manage the state of a FAB button
    val hideFabPostAnimation = remember { mutableStateOf(value = true) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularRevealAnimation(
            circularRevealAnimationVal = revealAnimation,
            animateButtonVal = animateButton,
            cardComposable = {
                CardComposableHandler {
                    revealAnimation.value = false   //To trigger card closing animation
                }
            },
            fabComposable = {
                FabAnimationHandler {
                    animateButton.value = true  //In order to start FAB animation on click event
                }
            },
//                            hideFabPostAnimationVal = hideFabPostAnimation,
            fabAnimationDur = 800,
            revealAnimDur = 800,
            fabCloseDelay = 800,    //FAB progress transition from 100% to 0%
            animationType = AnimationType.CIRCULAR_REVEAL,
            overlayBackgroundColor = MattePurple.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun CardComposableHandler(handleRevealAnim: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient( // Defined vertical gradient colors here
                    listOf(
                        DarkBlue, GridBackground
                    )
                )
            )
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
                            color = GolderYellow, textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(id = R.string.circular_reveal_body_copy_link_text_2))
                    }
                    append(text = " ")
                    append(text = stringResource(id = R.string.circular_reveal_body_copy_link_text_3))
                    pop()
                }

                Spacer(modifier = Modifier.height(8.dp))

                ClickableText(text = gameLink, style = TextStyle(
                    color = Beige,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Start
                ), modifier = Modifier.fillMaxWidth(), onClick = {
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
                        handleRevealAnim()
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
                        .weight(1f),
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

@Composable
fun FabAnimationHandler(handleAnimateButton: () -> Unit) {
    //  FAB without transition
    /*Image(
        modifier = Modifier
            .layoutId("mi_single_fab")
            .clickable {
                handleAnimateButton()
            },
        painter = painterResource(id = com.mindinventory.motionlayoutfabanimation.R.drawable.ic_hanuman_standing_pose),
        contentDescription = "Hanuman standing"
    )*/

    //  FAB with transition
    //  Handling initial transition
    Image(
        modifier = Modifier
            .layoutId("mi_fab_start")
            .clickable {
                handleAnimateButton()
            },
        painter = painterResource(id = com.mindinventory.motionlayoutfabanimation.R.drawable.ic_hanuman_standing_pose),
        contentDescription = "Hanuman standing"
    )
    //  Handling closure transition
    Image(
        modifier = Modifier
            .layoutId("mi_fab_end")
            .clickable {
                handleAnimateButton()
            },
        painter = painterResource(id = com.mindinventory.motionlayoutfabanimation.R.drawable.ic_hanuman_thumps_up),
        contentDescription = "Hanuman victory pose"
    )
}