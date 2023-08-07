package com.mindinventory.motionlayoutfabanimation.management

import androidx.compose.runtime.Stable

@Stable
interface MiToolbarState {
    val offset: Float
    val height: Float
    val progress: Float
    var scrollValue: Int
}