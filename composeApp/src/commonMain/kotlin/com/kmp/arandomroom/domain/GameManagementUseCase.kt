package com.kmp.arandomroom.domain

import com.kmp.arandomroom.data.model.ActionDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.GameStateDMO
import com.kmp.arandomroom.data.model.InteractableObjectDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.ItemDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.MoveDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.RoomDMO.Companion.toDTO
import com.kmp.arandomroom.data.repository.ActionRepository
import com.kmp.arandomroom.data.repository.GameRepository
import com.kmp.arandomroom.data.repository.InteractableObjectRepository
import com.kmp.arandomroom.data.repository.ItemRepository
import com.kmp.arandomroom.data.repository.MoveRepository
import com.kmp.arandomroom.data.repository.RoomRepository
import com.kmp.arandomroom.domain.model.ActionDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.GeneratedGame
import com.kmp.arandomroom.domain.model.InteractableObjectDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.RoomDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.ItemDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.MoveDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.RoomDTO

class GameManagementUseCase(
    private val gameRepository: GameRepository,
    private val roomRepository: RoomRepository,
    private val itemRepository: ItemRepository,
    private val interactableObjectDRepository: InteractableObjectRepository,
    private val actionRepository: ActionRepository,
    private val moveRepository: MoveRepository
) {

    suspend fun insertGame(gameId: String, generatedGame: GeneratedGame) {
        gameRepository.insertGame(
            GameStateDMO(
                id = gameId,
                title = generatedGame.title,
                currentRoom = generatedGame.currentRoom,
                endRoom = generatedGame.endRoom
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
            room.interactableObjects.forEach { interactableObject ->
                interactableObjectDRepository.insertInteractableObject(
                    interactableObject.toDMO(
                        gameId = gameId,
                        roomId = room.id
                    )
                )
            }

            room.moves.forEach { move ->
                moveRepository.insertMove(
                    move.toDMO(
                        gameId = gameId,
                        roomId = room.id
                    )
                )
            }
        }

        generatedGame.rooms.forEach { room ->
            room.actions.forEach { action ->
                actionRepository.insertAction(
                    action.toDMO(
                        gameId = gameId,
                        roomId = room.id
                    )
                )
            }
        }
    }

    suspend fun getAllGames(): List<GameStateDMO> {
        return gameRepository.getAllGames()
    }

    suspend fun deleteGame(gameId: String) {
        gameRepository.deleteGame(gameId)
    }

    suspend fun setRoomIsVisited(roomId: String) {
        roomRepository.setRoomIsVisited(roomId)
    }

    suspend fun getGameState(gameId: String): GameStateDMO {
        return gameRepository.getGameById(gameId)
    }

    suspend fun updateGameState(gameId: String, currentRoomId: String) {
        gameRepository.updateGameState(gameId, currentRoomId)
    }

    suspend fun getGameInventory(gameId: String): List<ItemDTO> {
        return itemRepository.getInventoryItemsForGame(gameId).map { it.toDTO() }
    }

    suspend fun getRoom(gameId: String, roomId: String): RoomDTO {
        val room = roomRepository.getRoom(roomId)
        val items = itemRepository.getAllItemsForRoom(
            roomId = gameId,
            gameId = roomId
        ).map { it.toDTO() }

        val interactableObjects = interactableObjectDRepository.getAllInteractableObjectsForRoom(
            roomId = gameId,
            gameId = roomId
        ).map { it.toDTO() }

        val actions = actionRepository.getAllActionsForRoom(
            roomId = gameId,
            gameId = roomId
        ).map { it.toDTO() }

        val moves = moveRepository.getMovesForRoom(
            gameId = gameId,
            roomId = roomId
        ).map { it.toDTO() }

        return room.toDTO(
            moves = moves,
            actions = actions,
            items = items,
            interactableObjects = interactableObjects
        )
    }
}