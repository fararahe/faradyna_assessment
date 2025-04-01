package com.faradyna.assessment.utility.extensions.compose

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.faradyna.assessment.utility.enums.FadeSide

private val DefaultWidth = 15.dp

fun Modifier.topFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    width: Dp = DefaultWidth,
    spec: AnimationSpec<Dp>? = null
) = fadingEdge(FadeSide.TOP, color = color, width = width, isVisible = isVisible, spec = spec)

fun Modifier.bottomFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    width: Dp = DefaultWidth,
    spec: AnimationSpec<Dp>? = null
) = fadingEdge(FadeSide.BOTTOM, color = color, width = width, isVisible = isVisible, spec = spec)

fun Modifier.rightFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    width: Dp = DefaultWidth,
    spec: AnimationSpec<Dp>? = null
) = fadingEdge(FadeSide.RIGHT, color = color, width = width, isVisible = isVisible, spec = spec)

fun Modifier.leftFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    width: Dp = DefaultWidth,
    spec: AnimationSpec<Dp>? = null
) = fadingEdge(FadeSide.LEFT, color = color, width = width, isVisible = isVisible, spec = spec)

fun Modifier.fadingEdge(
    vararg sides: FadeSide,
    color: Color,
    width: Dp = DefaultWidth,
    isVisible: Boolean = true,
    spec: AnimationSpec<Dp>? = null
) = composed {
    require(width > 0.dp) { "Invalid fade width: Width must be greater than 0" }

    val animatedWidth = spec?.let {
        animateDpAsState(
            targetValue = if (isVisible) width else 0.dp,
            animationSpec = spec,
            label = "Fade width"
        ).value
    }

    drawWithContent {
        this@drawWithContent.drawContent()

        sides.forEach { side ->
            val (start, end) = this.size.getFadeOffsets(side)

            val staticWidth = if (isVisible) width.toPx() else 0f
            val widthPx = animatedWidth?.toPx() ?: staticWidth

            val fraction = when(side) {
                FadeSide.LEFT, FadeSide.RIGHT -> widthPx / this.size.width
                FadeSide.BOTTOM, FadeSide.TOP -> widthPx / this.size.height
            }

            drawRect(
                brush = Brush.linearGradient(
                    0f to color,
                    fraction to Color.Transparent,
                    start = start,
                    end = end
                ),
                size = this.size
            )
        }
    }
}

fun Modifier.shimmerOverlay(isShimmering: Boolean = true): Modifier = composed {
    if (!isShimmering) return@composed this

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = -300f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value, 0f),
        end = Offset(translateAnim.value + 300f, 0f)
    )

    this.drawWithContent {
        drawContent() // Draw the original content
        drawRect(brush) // Overlay the shimmer effect
    }
}