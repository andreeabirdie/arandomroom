package com.kmp.arandomroom.ui.screens.menu

import com.kmp.arandomroom.data.model.GameStateDMO

data class MenuState(
    val isLoading: Boolean,
    val games: List<GameStateDMO>,
    val generatedGameId: String?,
    val error: String?
) {
    companion object {
        fun getDefaultState(): MenuState {
            return MenuState(
                isLoading = true,
                games = emptyList(),
                generatedGameId = null,
                error = null
            )
        }
    }
}