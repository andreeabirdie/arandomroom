package com.kmp.arandomroom.di

import com.kmp.arandomroom.domain.model.GameStateDTO
import com.kmp.arandomroom.ui.screens.menu.MenuViewModel
import com.kmp.arandomroom.ui.screens.room.RoomViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uiModule = module {
    viewModel<RoomViewModel> { (gameId: String) ->
        RoomViewModel(gameId, get(named("RoomGenerationUseCase")), get())
    }

    viewModel<MenuViewModel> {
        MenuViewModel(get(named("MenuGenerationUseCase")), get())
    }
}