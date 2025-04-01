package com.faradyna.assessment.utility.extensions.compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.faradyna.assessment.utility.enums.FadeSide

fun Size.getFadeOffsets(side: FadeSide): Pair<Offset, Offset> {
    return when (side) {
        FadeSide.LEFT -> Offset.Zero to Offset(width, 0f)
        FadeSide.RIGHT -> Offset(width, 0f) to Offset.Zero
        FadeSide.BOTTOM -> Offset(0f, height) to Offset.Zero
        FadeSide.TOP -> Offset.Zero to Offset(0f, height)
    }
}