package com.mindinventory.motionlayoutfabanimation

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindinventory.motionlayoutfabanimation.data.mockdata.populateList
import com.mindinventory.motionlayoutfabanimation.data.model.MiItem
import com.mindinventory.motionlayoutfabanimation.extra.animationScrollflags.MiExitUntilCollapsedState
import com.mindinventory.fabcardreveal.ui.theme.MotionLayoutFABAnimationTheme
import com.mindinventory.motionlayoutfabanimation.composables.MarioMotionHandler

val MinToolbarHeight = 96.dp
val MaxToolbarHeight = 176.dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenContent() {
    val marioToolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberSaveable(saver = MiExitUntilCollapsedState.Saver) {
        MiExitUntilCollapsedState(marioToolbarHeightRange)
    }
    val scrollState = rememberScrollState()
    toolbarState.scrollValue = scrollState.value

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            MarioMotionHandler(
                list = populateList(),
                columns = 2,
                modifier = Modifier.fillMaxSize(),
                scrollState = scrollState,
                progress = toolbarState.progress
            )
        })
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreenContent() {
    MotionLayoutFABAnimationTheme {
        MainScreenContent()
    }
}