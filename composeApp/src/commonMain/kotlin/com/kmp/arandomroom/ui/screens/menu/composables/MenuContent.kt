package com.kmp.arandomroom.ui.screens.menu.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.icon_logo
import com.kmp.arandomroom.ui.screens.composables.LoadingSquaresAnimation
import com.kmp.arandomroom.ui.screens.composables.PromptTextField
import com.kmp.arandomroom.ui.screens.composables.fadingEdgeAnimateDpAsState
import com.kmp.arandomroom.ui.screens.composables.leftFadingEdge
import com.kmp.arandomroom.ui.screens.composables.rightFadingEdge
import com.kmp.arandomroom.ui.screens.menu.MenuState
import org.jetbrains.compose.resources.painterResource

@Composable
fun MenuContent(
    prompt: String,
    lazyGridState: LazyStaggeredGridState,
    uiState: MenuState,
    onPromptChanged: (String) -> Unit,
    onStartGame: (String) -> Unit,
    onGenerate: () -> Unit,
) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            modifier = Modifier.height(200.dp),
            painter = painterResource(Res.drawable.icon_logo),
            contentDescription = "A Random Room logo"
        )
        PromptTextField(
            isEnabled = true,
            inputText = prompt,
            onInputChanged = onPromptChanged,
            placeholder = "Enter a theme"
        )
        Spacer(modifier = Modifier.height(20.dp))
        FilledTonalButton(
            enabled = !uiState.isLoading,
            onClick = onGenerate
        ) {
            Text(text = "Generate a new adventure")
        }

        if (uiState.games.isNotEmpty()) {
            OrDivider(modifier = Modifier.padding(top = 16.dp))
            Text(
                text = "Return to the familiar",
                modifier = Modifier.padding(16.dp)
            )
            LazyHorizontalStaggeredGrid(
                modifier = Modifier
                    .height(200.dp)
                    .leftFadingEdge(
                        color = MaterialTheme.colors.primary,
                        size = fadingEdgeAnimateDpAsState(
                            maxSize = 30.dp,
                            isVisible = lazyGridState.canScrollForward
                        ).value
                    )
                    .rightFadingEdge(
                        color = MaterialTheme.colors.primary,
                        size = fadingEdgeAnimateDpAsState(
                            maxSize = 30.dp,
                            isVisible = lazyGridState.canScrollBackward
                        ).value
                    ),
                state = lazyGridState,
                rows = StaggeredGridCells.Adaptive(50.dp),
                horizontalItemSpacing = 8.dp
            ) {
                items(uiState.games) { game ->
                    FilledTonalButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { onStartGame(game.id) }
                    ) {
                        Text(text = game.title)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}