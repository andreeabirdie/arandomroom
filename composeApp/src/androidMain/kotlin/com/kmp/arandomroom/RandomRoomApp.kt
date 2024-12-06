package com.kmp.arandomroom

import android.app.Application
import com.kmp.arandomroom.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class RandomRoomApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@RandomRoomApp)
        }
    }
}