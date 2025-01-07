package com.kmp.arandomroom

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kmp.arandomroom.ui.screens.Routes
import com.kmp.arandomroom.ui.screens.menu.MenuScreen
import com.kmp.arandomroom.ui.screens.room.RoomScreen
import com.kmp.arandomroom.ui.theme.AppTheme
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        AppTheme {
            val navController = rememberNavController()

            Surface {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Menu.route,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    composable(route = Routes.Menu.route) {
                        MenuScreen(
                            onStartGame = { gameId ->
                                navController.navigate(Routes.Room.withArgs(gameId))
                            }
                        )
                    }
                    composable(
                        route = Routes.Room.route,
                        arguments = listOf(navArgument("gameId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val gameId = backStackEntry.arguments?.getString("gameId")
                        RoomScreen(
                            gameId = gameId ?: "Unknown",
                            onExitGame = { navController.navigate(Routes.Menu.route) }
                        )
                    }
                }
            }
        }
    }
}