package com.kmp.arandomroom.ui.screens.room

import com.kmp.arandomroom.domain.model.GameStateDTO

data class RoomState(
    val gameStateDTO: GameStateDTO,
    val isLoading: Boolean
)