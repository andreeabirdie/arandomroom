package com.kmp.arandomroom.ui.screens.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.invalid_action_feedback
import arandomroom.composeapp.generated.resources.validate_action_prompt
import arandomroom.composeapp.generated.resources.error_message
import com.kmp.arandomroom.domain.GameManagementUseCase
import com.kmp.arandomroom.domain.model.RoomDTO
import com.kmp.arandomroom.domain.model.ValidatedAction
import com.kmp.arandomroom.domain.GenerationUseCase
import com.kmp.arandomroom.domain.model.ObjectDTO
import com.kmp.arandomroom.domain.model.ItemDTO
import com.kmp.arandomroom.domain.model.MoveDTO
import com.kmp.arandomroom.utils.joinSerializedObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent

class RoomViewModel(
    gameId: String,
    private val generationUseCase: GenerationUseCase,
    private val gameManagementUseCase: GameManagementUseCase
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(RoomState.getDefaultState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val gameState = gameManagementUseCase.getGameState(gameId)
            val inventory = gameManagementUseCase.getGameInventory(gameId)
            val currentRoom = gameManagementUseCase.getRoom(gameId, gameState.currentRoom)
            gameManagementUseCase.getAllRooms(gameId)
            _uiState.value = _uiState.value.copy(
                gameId = gameId,
                isLoading = false,
                currentRoom = currentRoom,
                endRoom = gameState.endRoom,
                inventory = inventory,
                actionFeedback = ""
            )
        }
    }

    fun onAction(action: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val currentRoom = _uiState.value.currentRoom

        viewModelScope.launch {
            var prompt = getString(
                Res.string.validate_action_prompt,
                action,
                currentRoom.moves.joinSerializedObjects(MoveDTO.serializer()),
                currentRoom.items.joinSerializedObjects(ItemDTO.serializer()),
                currentRoom.objects.joinSerializedObjects(ObjectDTO.serializer()),
                _uiState.value.inventory.joinSerializedObjects(ItemDTO.serializer()),
                currentRoom.description,
            )
            print("qwerty prompt: $prompt")
            val errorMessage = getString(Res.string.error_message)
            try {
                val response = generationUseCase.generateResponse(prompt)
                if (response != null) {
                    val validatedAction = Json.decodeFromString<ValidatedAction>(response)
                    performAction(currentRoom, validatedAction)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    actionFeedback = errorMessage
                )
            }
        }

    }

    private suspend fun performAction(
        currentRoom: RoomDTO,
        validatedAction: ValidatedAction
    ) {
        println("qwerty $validatedAction")
        val feedback = validatedAction.actionFeedback.ifEmpty {
            getString(Res.string.invalid_action_feedback)
        }

        if (validatedAction.actionId == null) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                actionFeedback = feedback
            )
            return
        }

        currentRoom.moves.firstOrNull { it.id == validatedAction.actionId }?.let { moveDTO ->
            performMove(moveDTO, feedback)
            return
        }

        currentRoom.items.forEach {
            println("qwerty item: $it")
        }
        currentRoom.items.firstOrNull { it.id == validatedAction.actionId }?.let { itemDTO ->
            performPickUp(itemDTO, feedback)
            return
        }

        currentRoom.objects.firstOrNull { it.id == validatedAction.actionId }?.let { objectDTO ->
            performObjectInteraction(objectDTO, feedback)
            return
        }

        _uiState.value = _uiState.value.copy(
            isLoading = false,
            actionFeedback = getString(Res.string.error_message)
        )
    }

    private suspend fun performMove(move: MoveDTO, feedback: String) {
        println("qwerty performing move $move, $feedback")
        val nextRoom = gameManagementUseCase.getRoom(
            gameId = _uiState.value.gameId,
            roomId = move.roomDestinationId
        )
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            currentRoom = nextRoom,
            actionFeedback = feedback
        )
        gameManagementUseCase.setRoomIsVisited(_uiState.value.currentRoom.id)
        gameManagementUseCase.updateGameState(
            gameId = _uiState.value.gameId,
            currentRoomId = nextRoom.id
        )
    }

    private suspend fun performPickUp(item: ItemDTO, feedback: String) {
        println("qwerty picking up $item, $feedback")
        gameManagementUseCase.setItemIsInInventory(item.id)
        val inventory = gameManagementUseCase.getGameInventory(_uiState.value.gameId)
        val currentRoom = gameManagementUseCase.getRoom(
            gameId = _uiState.value.gameId,
            roomId = _uiState.value.currentRoom.id
        )
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            currentRoom = currentRoom,
            inventory = inventory,
            actionFeedback = feedback
        )
    }

    private fun performObjectInteraction(objectDTO: ObjectDTO, feedback: String) {
        println("qwerty interacting with $objectDTO, $feedback")
        // todo: don't trust ai, validate object interaction
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            actionFeedback = feedback
        )
    }
}