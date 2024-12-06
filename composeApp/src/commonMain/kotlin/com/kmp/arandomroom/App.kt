package com.kmp.arandomroom

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kmp.arandomroom.data.model.GameState
import com.kmp.arandomroom.ui.screens.RandomRoomScreen
import com.kmp.arandomroom.ui.screens.final.FinalScreen
import com.kmp.arandomroom.ui.screens.menu.MenuScreen
import com.kmp.arandomroom.ui.screens.room.RoomScreen
import com.kmp.arandomroom.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        var initialGameState : GameState? = null

        Surface {
            NavHost(
                navController = navController,
                startDestination = RandomRoomScreen.Menu.route,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                composable(route = RandomRoomScreen.Menu.route) {
                    MenuScreen(
                        onStartGame = { gameState ->
                            initialGameState = gameState
                            navController.navigate(RandomRoomScreen.Room.route) }
                    )
                }
                composable(route = RandomRoomScreen.Room.route) {
                    initialGameState?.let { gameState ->
                        RoomScreen(
                            initialGameState = gameState,
                            onEndGame = { navController.navigate(RandomRoomScreen.Final.route) },
                            onExitGame = { navController.navigate(RandomRoomScreen.Menu.route) }
                        )
                    }
                }
                composable(route = RandomRoomScreen.Final.route) {
                    FinalScreen(
                        onExitGame = { navController.navigate(RandomRoomScreen.Menu.route) }
                    )
                }
            }
        }
    }
}