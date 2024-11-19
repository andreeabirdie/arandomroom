package com.kmp.arandomroom.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kmp.arandomroom.data.model.GameState
import com.kmp.arandomroom.ui.screens.room.composables.PromptTextField
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun MenuScreen(
    onStartGame: (GameState?) -> Unit,
    viewModel: MenuViewModel = getViewModel(Unit, viewModelFactory { MenuViewModel() })
) {
    val prompt = remember { mutableStateOf("") }
    val generatedGameState = viewModel.uiState.collectAsState()
    val isLoading = remember { mutableStateOf(false) }

    generatedGameState.value?.let { gameStateDTO ->
        onStartGame(
            GameState(
                currentRoom = gameStateDTO.currentRoom,
                endRoom = gameStateDTO.endRoom,
                rooms = gameStateDTO.rooms,
                actionFeedback = "",
                inventory = emptyList()
            )
        )
        isLoading.value = false
    }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to A Random Room!")
        Spacer(modifier = Modifier.height(20.dp))
        PromptTextField(
            isEnabled = true,
            inputText = prompt,
            placeholder = "Enter a theme"
        )
        Spacer(modifier = Modifier.height(20.dp))
        FilledTonalButton(
            enabled = !isLoading.value,
            onClick = {
                isLoading.value = true
                viewModel.generateGame(prompt.value)
            },
        ) {
            Text("Generate a random room")
        }
    }
}