package com.kmp.arandomroom.ui.screens.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.rightFadingEdge(
    color: Color,
    size: Dp
): Modifier = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()

        val fraction = size.toPx() / this.size.width
        val brush = Brush.horizontalGradient(0f to Color.Transparent, fraction to color)

        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

fun Modifier.leftFadingEdge(
    color: Color,
    size: Dp,
): Modifier = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()

        val fraction = 1f - (size.toPx() / this.size.width)
        val brush = Brush.horizontalGradient(fraction to color, 1f to Color.Transparent)

        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
fun fadingEdgeAnimateDpAsState(
    maxSize: Dp,
    isVisible: Boolean,
    label: String = "Fading edge DpAnimation"
): State<Dp> {
    return animateDpAsState(
        targetValue = if (isVisible) maxSize else 0.dp,
        animationSpec = SpringSpec(
            Spring.DampingRatioNoBouncy,
            Spring.StiffnessLow,
            Dp.VisibilityThreshold
        ),
        label = label
    )
}