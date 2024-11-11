package com.kmp.arandomroom

import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.kmp.arandomroom.ui.screens.game.RoomScreen
import com.kmp.arandomroom.ui.screens.game.GameViewModel
import com.kmp.arandomroom.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
@Preview
fun App() {
    AppTheme {
        val gameViewModel = getViewModel(Unit, viewModelFactory { GameViewModel() })
        val uiState by gameViewModel.uiState.collectAsState()

        Surface {
            RoomScreen(
                gameState = uiState,
                onAction = gameViewModel::updateGameState
            )
        }
    }
}