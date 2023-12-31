package com.inkindpro.motionlayoutfabanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.inkindpro.motionlayoutfabanimation.ui.theme.GolderYellow
import com.inkindpro.motionlayoutfabanimation.ui.theme.MotionLayoutFABAnimationTheme
import com.inkindpro.motionlayoutfabanimation.ui.theme.OffWhite

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutFABAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
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

@OptIn(ExperimentalMotionApi::class, ExperimentalUnitApi::class)
@Composable
fun FABAnimation() {
    val context = LocalContext.current
    //Animate button method
    var animateButton by remember { mutableStateOf(false) }
    //Button animation progress
    val animationProgress by animateFloatAsState(
        targetValue = if (animateButton) 1f else 0f,
        animationSpec = tween(1000) // keeping animation specific duration to 1 sec
    )
    // To include raw/JSON5-script file
    val motionScene: String = remember {
        context.resources.openRawResource(R.raw.motion_scene_fab)
            .readBytes()    //Reading bytes as we want MotionScene in a String format
            .decodeToString()
    }

    MotionLayout(
        motionScene = MotionScene(motionScene),
        progress = animationProgress,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        //Button - 1
        Button(
            onClick = {
                animateButton = !animateButton
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
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Americano",
                    color = OffWhite,
                    fontSize = TextUnit(value = 18F, type = TextUnitType.Sp)
                )
            }
        }

        //Button - 2
        Button(
            onClick = {
                animateButton = !animateButton
            },
            colors = ButtonDefaults.buttonColors(containerColor = GolderYellow),
            modifier = Modifier.layoutId("btn_lemon_tea"),
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_lemon_tea),
                    contentDescription = "Lemon tea",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Iced Lemon Tea",
                    color = OffWhite,
                    fontSize = TextUnit(value = 18F, type = TextUnitType.Sp)
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