package com.kmp.arandomroom.di

import com.kmp.arandomroom.domain.GameManagementUseCase
import com.kmp.arandomroom.domain.model.GeneratedGame
import com.kmp.arandomroom.domain.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerationUseCase
import com.kmp.arandomroom.domain.model.RoomDescription
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    single<GenerationUseCase>(named("RoomGenerationUseCase")) {
        GenerationUseCase(ValidatedAction.getSchema())
    }

    single<GenerationUseCase>(named("MenuGenerationUseCase")) {
        GenerationUseCase(GeneratedGame.getSchema())
    }

    single<GenerationUseCase>(named("UpdateRoomDescriptionUseCase")) {
        GenerationUseCase(RoomDescription.getSchema())
    }

    singleOf(::GameManagementUseCase).bind(GameManagementUseCase::class)
}