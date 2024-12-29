package com.kmp.arandomroom.ui.screens.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

@Composable
fun AnimatedTextSwitcher(
    messages: List<String>,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableStateOf(0) }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(currentIndex) {
        visible = true
        delay(2000)
        visible = false
        delay(500)
        currentIndex = (currentIndex + 1) % messages.size
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                initialOffsetY = { it }
            ),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(
                targetOffsetY = { it }
            )
        ) {
            Text(
                text = messages[currentIndex],
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
