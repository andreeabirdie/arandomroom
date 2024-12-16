package com.kmp.arandomroom.domain.model

import com.kmp.arandomroom.data.model.GameStateDMO
import kotlinx.serialization.Serializable

@Serializable
data class GameStateDTO(
    val gameId: String,
    val title: String,
    val currentRoom: String,
    val endRoom: String,
    val rooms: List<RoomDTO>,
    val actionFeedback: String,
    val inventory: List<ItemDTO>
) {
    companion object {
        fun GameStateDTO.toDMO(): GameStateDMO {
           return GameStateDMO(
                id = gameId,
                title = title,
                currentRoom = currentRoom,
                endRoom = endRoom,
                actionFeedback = actionFeedback
            )
        }
    }
}