package com.kmp.arandomroom

import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.kmp.arandomroom.ui.screens.room.RoomScreen
import com.kmp.arandomroom.ui.screens.room.RoomViewModel
import com.kmp.arandomroom.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
@Preview
fun App() {
    AppTheme {
        val roomViewModel = getViewModel(Unit, viewModelFactory { RoomViewModel() })
        val uiState by roomViewModel.uiState.collectAsState()

        Surface {
            RoomScreen(
                gameState = uiState,
                onAction = roomViewModel::updateGameState
            )
        }
    }
}