package com.kmp.arandomroom.di

import com.kmp.arandomroom.data.model.GeneratedGame
import com.kmp.arandomroom.data.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerationUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    single<GenerationUseCase>(named("RoomGenerationUseCase")) {
        GenerationUseCase(ValidatedAction.getSchema())
    }

    single<GenerationUseCase>(named("MenuGenerationUseCase")) {
        GenerationUseCase(GeneratedGame.getSchema())
    }
}