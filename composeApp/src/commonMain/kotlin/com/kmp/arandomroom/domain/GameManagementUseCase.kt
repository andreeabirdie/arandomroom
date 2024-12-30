package com.kmp.arandomroom.domain

import com.kmp.arandomroom.data.model.ActionDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.GameStateDMO
import com.kmp.arandomroom.data.model.GameStateDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.InteractableObjectDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.ItemDMO.Companion.toDTO
import com.kmp.arandomroom.data.model.RoomDMO.Companion.toDTO
import com.kmp.arandomroom.data.repository.ActionRepository
import com.kmp.arandomroom.data.repository.GameRepository
import com.kmp.arandomroom.data.repository.InteractableObjectRepository
import com.kmp.arandomroom.data.repository.ItemRepository
import com.kmp.arandomroom.data.repository.RoomRepository
import com.kmp.arandomroom.domain.model.ActionDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.GameStateDTO
import com.kmp.arandomroom.domain.model.GameStateDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.InteractableObjectDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.RoomDTO.Companion.toDMO
import com.kmp.arandomroom.domain.model.ItemDTO.Companion.toDMO

class GameManagementUseCase(
    private val gameRepository: GameRepository,
    private val roomRepository: RoomRepository,
    private val itemRepository: ItemRepository,
    private val interactableObjectDRepository: InteractableObjectRepository,
    private val actionRepository: ActionRepository
) {

    suspend fun insertGame(gameStateDTO: GameStateDTO) {
        gameRepository.insertGame(gameStateDTO.toDMO())
        gameStateDTO.rooms.forEach { room ->
            roomRepository.insertRoom(room.toDMO(gameStateDTO.gameId))
            room.items.forEach { item ->
                val isInInventory = gameStateDTO.inventory.any { inventoryItem ->
                    inventoryItem.id == item.id
                }
                itemRepository.insertItem(
                    item.toDMO(
                        gameId = gameStateDTO.gameId,
                        roomId = room.id,
                        isInInventory = isInInventory
                    )
                )
            }
        }

        gameStateDTO.rooms.forEach { room ->
            room.interactableObjects.forEach { interactableObject ->
                interactableObjectDRepository.insertInteractableObject(
                    interactableObject.toDMO(
                        gameId = gameStateDTO.gameId,
                        roomId = room.id
                    )
                )
            }
        }

        gameStateDTO.rooms.forEach { room ->
            room.actions.forEach { action ->
                actionRepository.insertAction(
                    action.toDMO(
                        gameId = gameStateDTO.gameId,
                        roomId = room.id
                    )
                )
            }
        }
    }

    suspend fun getAllGames() : List<GameStateDMO> {
        return gameRepository.getAllGames()
    }

    suspend fun deleteGame(gameId: String) {
        gameRepository.deleteGame(gameId)
    }

    suspend fun getGameState(gameId: String): GameStateDTO {
        val rooms = roomRepository.getAllRoomsForGame(gameId).map { room ->
            val items = itemRepository.getAllItemsForRoom(
                roomId = gameId,
                gameId = room.id
            ).map { it.toDTO() }

            val interactableObjects =
                interactableObjectDRepository.getAllInteractableObjectsForRoom(
                    roomId = gameId,
                    gameId = room.id
                ).map { it.toDTO() }

            val actions = actionRepository.getAllActionsForRoom(
                roomId = gameId,
                gameId = room.id
            ).map { it.toDTO() }

            return@map room.toDTO(
                actions = actions,
                items = items,
                interactableObjects = interactableObjects
            )
        }

        val inventory = itemRepository.getInventoryItemsForGame(gameId).map { it.toDTO() }
        return gameRepository.getGameById(gameId).toDTO(rooms, inventory)
    }
}