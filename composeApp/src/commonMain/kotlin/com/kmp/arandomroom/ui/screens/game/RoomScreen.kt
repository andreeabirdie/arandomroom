package com.kmp.arandomroom.ui.screens.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.icon_send
import com.kmp.arandomroom.data.model.GameState
import com.kmp.arandomroom.ui.screens.game.composables.AnimatedText
import com.kmp.arandomroom.ui.screens.game.composables.PromptTextField
import org.jetbrains.compose.resources.vectorResource

@Composable
fun RoomScreen(
    gameState: GameState,
    onAction: (String) -> Unit
) {
    val prompt = remember { mutableStateOf("") }
    val currentRoom = gameState.rooms.first { it.id == gameState.currentRoom }
    val animationOngoing = remember { mutableStateOf(true) }

    if (currentRoom.id == gameState.endRoom) {
        // todo: end game
    }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You are in ${currentRoom.name}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(30.dp))
            AnimatedText(
                text = currentRoom.description,
                style = MaterialTheme.typography.titleMedium,
                animationOngoing = animationOngoing
            )
        }

        Column {
            AnimatedText(
                gameState.actionFeedback,
                style = MaterialTheme.typography.titleSmall,
                animationOngoing = mutableStateOf(false)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                PromptTextField(
                    isEnabled = !animationOngoing.value,
                    inputText = prompt,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surfaceDim)
                        .padding(top = 8.dp)
                        .weight(1f)
                )

                AnimatedVisibility(visible = prompt.value.isNotEmpty()) {
                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            onAction(prompt.value.lowercase())
                            prompt.value = ""
                        }
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            imageVector = vectorResource(Res.drawable.icon_send),
                            contentDescription = "Send"
                        )
                    }
                }
            }
        }
    }
}