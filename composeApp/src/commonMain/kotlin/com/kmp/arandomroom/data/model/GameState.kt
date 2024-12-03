package com.kmp.arandomroom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val currentRoom: String,
    val endRoom: String,
    val rooms: List<Room>,
    val actionFeedback: String,
    val inventory: List<String>
) {
     companion object {
         fun getDefaultGameState() : GameState {
             return GameState(
                 currentRoom = "",
                 endRoom = "",
                 rooms = emptyList(),
                 actionFeedback = "",
                 inventory = emptyList()
             )
         }
     }
}