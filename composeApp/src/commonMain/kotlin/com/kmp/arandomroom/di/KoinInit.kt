package com.kmp.arandomroom.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration.invoke(this)
        modules(
            domainModule,
            uiModule
        )
    }
}
