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
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kmp.arandomroom.ui.screens.menu.composables.LoadingMenuContent
import com.kmp.arandomroom.ui.screens.menu.composables.MenuContent
import com.kmp.arandomroom.ui.screens.room.composables.PromptTextField
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun MenuScreen(
    onStartGame: (String) -> Unit,
    viewModel: MenuViewModel = koinViewModel<MenuViewModel>()
) {
    val prompt = remember { mutableStateOf("") }
    val uiState = viewModel.uiState.collectAsState()
    val clickedGenerate = remember { mutableStateOf(false) }
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    uiState.value.generatedGameId?.let { gameId ->
        onStartGame(gameId)
    }

    when {
        uiState.value.isLoading -> LoadingMenuContent(clickedGenerate.value)
        else -> {
            MenuContent(
                prompt = prompt.value,
                lazyGridState = lazyStaggeredGridState,
                uiState = uiState.value,
                onPromptChanged = { prompt.value = it },
                onStartGame = onStartGame,
                onDeleteGame = { gameId -> viewModel.deleteGame(gameId) },
                onGenerate = {
                    clickedGenerate.value = true
                    viewModel.generateGame(prompt.value)
                }
            )
        }
    }
}