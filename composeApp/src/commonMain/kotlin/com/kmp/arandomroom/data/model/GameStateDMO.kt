package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameStateDMO(
    @PrimaryKey val id: String,
    val title: String,
    val currentRoom: String,
    val endRoom: String
)