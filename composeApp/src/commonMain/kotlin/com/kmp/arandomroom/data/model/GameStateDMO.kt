package com.kmp.arandomroom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kmp.arandomroom.domain.model.GameStateDTO
import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.RoomDTO

@Entity(tableName = "games")
data class GameStateDMO(
    @PrimaryKey val id: String,
    val title: String,
    val currentRoom: String,
    val endRoom: String,
    val actionFeedback: String
) {
    companion object {
        fun GameStateDMO.toDTO(rooms: List<RoomDTO>, inventory: List<ItemDTO>): GameStateDTO {
            return GameStateDTO(
                gameId = id,
                title = title,
                currentRoom = currentRoom,
                endRoom = endRoom,
                actionFeedback = actionFeedback,
                rooms = rooms,
                inventory = inventory
            )
        }
    }
}