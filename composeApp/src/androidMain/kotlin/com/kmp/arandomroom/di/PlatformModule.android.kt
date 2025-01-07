package com.kmp.arandomroom.di

import com.kmp.arandomroom.data.database.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single { getDatabaseBuilder(get()) }
}