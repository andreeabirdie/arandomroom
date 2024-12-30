package com.kmp.arandomroom.ui.screens.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun LoadingSquaresAnimation(
    squareSize: Float,
    modifier: Modifier = Modifier,
    isCentered: Boolean = true,
    squareColor: Color = MaterialTheme.colorScheme.primary
) {
    val initialSpacing = 2 * squareSize

    val square1 = remember { Animatable(0f) }
    val square2 = remember { Animatable(initialSpacing) }
    val square3 = remember { Animatable(2 * initialSpacing) }

    LaunchedEffect(Unit) {
        launch {
            while (true) {
                square1.animateTo(
                    targetValue = initialSpacing - squareSize,
                    animationSpec = tween(durationMillis = 300)
                )
                square2.animateTo(
                    targetValue = 2 * initialSpacing - squareSize,
                    animationSpec = tween(durationMillis = 300)
                )

                square3.animateTo(
                    targetValue = 3 * initialSpacing - squareSize,
                    animationSpec = tween(durationMillis = 300)
                )

                square3.animateTo(
                    targetValue = 2 * initialSpacing,
                    animationSpec = tween(durationMillis = 300)
                )

                square2.animateTo(
                    targetValue = initialSpacing,
                    animationSpec = tween(durationMillis = 300)
                )

                square1.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300)
                )
            }
        }
    }

    Canvas(modifier = modifier.fillMaxWidth()) {
        val startX = if (isCentered) { (size.width - 3 * initialSpacing ) / 2 } else 0f

        drawRect(
            squareColor,
            topLeft = Offset(startX + square1.value, size.height / 2 - squareSize / 2),
            size = Size(squareSize, squareSize)
        )
        drawRect(
            squareColor,
            topLeft = Offset(startX + square2.value, size.height / 2 - squareSize / 2),
            size = Size(squareSize, squareSize)
        )
        drawRect(
            squareColor,
            topLeft = Offset(startX + square3.value, size.height / 2 - squareSize / 2),
            size = Size(squareSize, squareSize)
        )
    }
}
