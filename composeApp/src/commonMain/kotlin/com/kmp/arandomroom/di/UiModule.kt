package com.kmp.arandomroom.di

import com.kmp.arandomroom.ui.screens.menu.MenuViewModel
import com.kmp.arandomroom.ui.screens.room.RoomViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uiModule = module {
    viewModel<RoomViewModel> { (gameId: String) ->
        RoomViewModel(
            gameId = gameId,
            getActionUseCase = get(named("RoomGenerationUseCase")),
            updateRoomDescriptionUseCase = get(named("UpdateRoomDescriptionUseCase")),
            gameManagementUseCase = get()
        )
    }

    viewModel<MenuViewModel> {
        MenuViewModel(
            generationUseCase = get(named("MenuGenerationUseCase")),
            gameManagementUseCase = get()
        )
    }
}