package com.kmp.arandomroom.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<GameDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("games.db")
    return Room.databaseBuilder<GameDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}