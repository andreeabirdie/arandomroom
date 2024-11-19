package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GameStateDTO(
    val currentRoom: String,
    val endRoom: String,
    val rooms: List<Room>
) {
    companion object {
        fun getDefaultGameState() : GameStateDTO {
            return GameStateDTO(
                currentRoom = "",
                endRoom = "",
                rooms = listOf(Room.getDefaultRoom())
            )
        }
    }
}