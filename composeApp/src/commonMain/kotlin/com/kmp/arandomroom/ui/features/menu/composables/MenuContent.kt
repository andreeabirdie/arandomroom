package com.kmp.arandomroom.ui.features.menu.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.game_generation_placeholder
import arandomroom.composeapp.generated.resources.generate_new_game_title
import arandomroom.composeapp.generated.resources.icon_logo
import arandomroom.composeapp.generated.resources.play_existing_game_title
import com.kmp.arandomroom.ui.features.composables.PromptTextField
import com.kmp.arandomroom.ui.features.composables.fadingEdgeAnimateDpAsState
import com.kmp.arandomroom.ui.features.composables.leftFadingEdge
import com.kmp.arandomroom.ui.features.composables.rightFadingEdge
import com.kmp.arandomroom.ui.features.menu.MenuState
import com.kmp.arandomroom.ui.features.room.composables.AnimatedText
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MenuContent(
    prompt: String,
    lazyGridState: LazyStaggeredGridState,
    uiState: MenuState,
    onPromptChanged: (String) -> Unit,
    onStartGame: (String) -> Unit,
    onDeleteGame: (String) -> Unit,
    onGenerate: () -> Unit,
) {
    Column(
        Modifier
            .padding(vertical = 20.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.height(200.dp),
                painter = painterResource(Res.drawable.icon_logo),
                contentDescription = "A Random Room logo"
            )

            uiState.error?.let { errorMessage ->
                AnimatedText(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = errorMessage,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }

            PromptTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                isEnabled = true,
                inputText = prompt,
                onInputChanged = onPromptChanged,
                placeholder = stringResource(Res.string.game_generation_placeholder)
            )

            FilledTonalButton(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                enabled = prompt.isNotEmpty(),
                onClick = onGenerate
            ) {
                Text(
                    text = stringResource(Res.string.generate_new_game_title),
                    textAlign = TextAlign.Center
                )
            }
        }

        AnimatedVisibility(
            visible = uiState.games.isNotEmpty(),
        ) {
            val selectedGame = remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OrDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(Res.string.play_existing_game_title),
                )

                LazyHorizontalStaggeredGrid(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .height(200.dp)
                        .leftFadingEdge(
                            color = MaterialTheme.colorScheme.primary,
                            size = fadingEdgeAnimateDpAsState(
                                maxSize = 30.dp,
                                isVisible = lazyGridState.canScrollForward
                            ).value
                        )
                        .rightFadingEdge(
                            color = MaterialTheme.colorScheme.primary,
                            size = fadingEdgeAnimateDpAsState(
                                maxSize = 30.dp,
                                isVisible = lazyGridState.canScrollBackward
                            ).value
                        ),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    state = lazyGridState,
                    rows = StaggeredGridCells.Adaptive(50.dp),
                    horizontalItemSpacing = 8.dp
                ) {
                    items(
                        items = uiState.games,
                        key = { it.id }
                    ) { game ->
                        TitleGameButton(
                            game = game,
                            isLongPressed = selectedGame.value == game.id,
                            onLongPress = { gameId ->
                                if (selectedGame.value == gameId) {
                                    selectedGame.value = ""
                                } else {
                                    selectedGame.value = gameId
                                }
                            },
                            onStartGame = onStartGame,
                            onDeleteGame = onDeleteGame
                        )
                    }
                }
            }
        }
    }
}