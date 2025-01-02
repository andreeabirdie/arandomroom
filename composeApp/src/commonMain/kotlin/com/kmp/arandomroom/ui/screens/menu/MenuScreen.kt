package com.kmp.arandomroom.ui.screens.menu

import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.kmp.arandomroom.ui.screens.menu.composables.LoadingMenuContent
import com.kmp.arandomroom.ui.screens.menu.composables.MenuContent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

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