package com.mindinventory.motionlayoutfabanimation.data.model

import androidx.annotation.DrawableRes

data class MiItem(
    val itemName: String? = null,
    val itemDescription: String? = null,
    @DrawableRes val itemImage: Int? = null,
)