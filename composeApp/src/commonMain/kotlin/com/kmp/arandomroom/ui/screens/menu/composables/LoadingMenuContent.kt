package com.kmp.arandomroom.ui.screens.menu.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.almost_there
import arandomroom.composeapp.generated.resources.creating_puzzles
import arandomroom.composeapp.generated.resources.dropping_keys
import arandomroom.composeapp.generated.resources.generating_rooms
import arandomroom.composeapp.generated.resources.hiding_treasures
import arandomroom.composeapp.generated.resources.just_a_moment
import arandomroom.composeapp.generated.resources.loading_game
import arandomroom.composeapp.generated.resources.locking_chests
import arandomroom.composeapp.generated.resources.placing_traps
import arandomroom.composeapp.generated.resources.unveiling_mysteries
import com.kmp.arandomroom.ui.screens.composables.AnimatedTextSwitcher
import com.kmp.arandomroom.ui.screens.composables.LoadingSquaresAnimation
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoadingMenuContent(clickedGenerate: Boolean) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingSquaresAnimation(squareSize = 40f)

        if (clickedGenerate) {
            AnimatedTextSwitcher(
                modifier = Modifier.padding(top = 20.dp),
                messages = listOf(
                    stringResource(Res.string.generating_rooms),
                    stringResource(Res.string.creating_puzzles),
                    stringResource(Res.string.just_a_moment),
                    stringResource(Res.string.locking_chests),
                    stringResource(Res.string.dropping_keys),
                    stringResource(Res.string.almost_there),
                    stringResource(Res.string.hiding_treasures),
                    stringResource(Res.string.placing_traps),
                    stringResource(Res.string.loading_game),
                    stringResource(Res.string.unveiling_mysteries)
                )
            )
        }
    }
}