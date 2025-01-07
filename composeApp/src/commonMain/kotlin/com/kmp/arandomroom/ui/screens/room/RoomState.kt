package com.kmp.arandomroom.ui.screens.room

import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.RoomDTO

data class RoomState(
    val gameId: String,
    val isLoading: Boolean,
    val currentRoom: RoomDTO,
    val endRoom: String,
    val inventory: List<ItemDTO>,
    val actionFeedback: String
) {
    companion object {
        fun getDefaultState(): RoomState {
            return RoomState(
                gameId = "",
                isLoading = true,
                currentRoom = RoomDTO(
                    id = "",
                    description = "",
                    moves = emptyList(),
                    name = "",
                    isVisited = false,
                    items = emptyList()
                ),
                endRoom = "",
                inventory = emptyList(),
                actionFeedback = ""
            )
        }
    }
}