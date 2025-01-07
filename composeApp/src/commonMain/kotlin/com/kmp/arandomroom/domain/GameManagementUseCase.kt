package com.kmp.arandomroom.domain

import com.kmp.arandomroom.data.model.GameStateDMO
import com.kmp.arandomroom.data.model.ItemDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.MoveDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.RoomDMO.Companion.toDTO
import com.kmp.arandomroom.data.repository.GameRepository
import com.kmp.arandomroom.data.repository.ItemRepository
import com.kmp.arandomroom.data.repository.MoveRepository
import com.kmp.arandomroom.data.repository.RoomRepository
import com.kmp.arandomroom.domain.model.GeneratedGame
import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.ItemDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.MoveDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.RoomDTO
import com.kmp.arandomroom.domain.model.RoomDTO.Companion.toDMO
import io.github.aakira.napier.Napier

class GameManagementUseCase(
    private val gameRepository: GameRepository,
    private val roomRepository: RoomRepository,
    private val itemRepository: ItemRepository,
    private val moveRepository: MoveRepository
) {

    suspend fun getAllGames(): List<GameStateDMO> {
        return gameRepository.getAllGames()
    }

    suspend fun insertGame(gameId: String, generatedGame: GeneratedGame) {
        gameRepository.insertGame(
            GameStateDMO(
                id = gameId,
                title = generatedGame.title,
                currentRoom = generatedGame.currentRoom,
                endRoom = generatedGame.endRoom,
                initialRoom = generatedGame.currentRoom
            )
        )
        generatedGame.rooms.forEach { room ->
            roomRepository.insertRoom(room.toDMO(gameId))
            room.items.forEach { item ->
                itemRepository.insertItem(
                    item.toDMO(
                        gameId = gameId,
                        roomId = room.id,
                        isInInventory = false
                    )
                )
            }
        }

        generatedGame.rooms.forEach { room ->
            room.moves.forEach { move ->
                moveRepository.insertMove(
                    move.toDMO(
                        gameId = gameId,
                        roomId = room.id
                    )
                )
            }
        }
    }

    suspend fun getGameState(gameId: String): GameStateDMO {
        return gameRepository.getGameById(gameId)
    }

    suspend fun getGameInventory(gameId: String): List<ItemDTO> {
        return itemRepository.getInventoryItemsForGame(gameId).map { it.toDTO() }
    }

    suspend fun getRoom(gameId: String, roomId: String): RoomDTO {
        val room = roomRepository.getRoom(roomId)
        val items = itemRepository.getAllItemsForRoom(
            roomId = roomId,
            gameId = gameId
        ).map { it.toDTO() }

        val moves = moveRepository.getMovesForRoom(
            gameId = gameId,
            roomId = roomId
        ).map { it.toDTO() }

        return room.toDTO(
            moves = moves,
            items = items
        )
    }

    suspend fun updateRoomDescription(gameId: String, roomId: String, description: String) {
        roomRepository.updateRoomDescription(gameId, roomId, description)
    }

    suspend fun setRoomIsVisited(roomId: String, isVisited: Boolean) {
        roomRepository.setRoomIsVisited(roomId, isVisited)
    }

    suspend fun setItemIsInInventory(itemId: String, isInInventory: Boolean) {
        itemRepository.setItemIsInInventory(itemId, isInInventory)
    }

    suspend fun setCurrentRoom(gameId: String, currentRoomId: String) {
        gameRepository.updateGameState(gameId, currentRoomId)
    }

    suspend fun resetGame(gameId: String) {
        val rooms = roomRepository.getAllRoomsForGame(gameId)
        val game = gameRepository.getGameById(gameId)
        val items = itemRepository.getInventoryItemsForGame(gameId)

        rooms.forEach { room -> setRoomIsVisited(room.id, false) }
        rooms.forEach { room -> updateRoomDescription(gameId, room.id, room.initialDescription) }
        setCurrentRoom(gameId, game.initialRoom)
        items.forEach { item -> setItemIsInInventory(item.id, false) }
    }

    suspend fun deleteGame(gameId: String) {
        gameRepository.deleteGame(gameId)
    }
}