package com.kmp.arandomroom.di

import com.kmp.arandomroom.data.database.GameDatabase
import com.kmp.arandomroom.data.database.getRoomDatabase
import com.kmp.arandomroom.data.repository.GameRepository
import com.kmp.arandomroom.data.repository.ItemRepository
import com.kmp.arandomroom.data.repository.MoveRepository
import com.kmp.arandomroom.data.repository.RoomRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {

    single<GameDatabase> { getRoomDatabase(get()) }

    singleOf(::GameRepository).bind(GameRepository::class)
    singleOf(::RoomRepository).bind(RoomRepository::class)
    singleOf(::ItemRepository).bind(ItemRepository::class)
    singleOf(::MoveRepository).bind(MoveRepository::class)
}