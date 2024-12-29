package com.kmp.arandomroom.ui.screens.menu.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kmp.arandomroom.ui.screens.composables.AnimatedTextSwitcher
import com.kmp.arandomroom.ui.screens.composables.LoadingSquaresAnimation

@Composable
fun LoadingMenuContent(clickedGenerate: Boolean) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingSquaresAnimation(squareSize = 40f)

        if (clickedGenerate) {
            AnimatedTextSwitcher(
                modifier = Modifier.padding(top = 20.dp),
                messages = listOf(
                    "Generating rooms",
                    "Creating puzzles",
                    "Just a moment",
                    "Locking chests",
                    "Dropping keys",
                    "Almost there",
                    "Hiding treasures",
                    "Placing traps",
                    "Loading game",
                    "Unveiling mysteries"
                )
            )
        }
    }
}